package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.request.CotizarEstanciaRequest;
import co.edu.uniquindio.sga.application.dto.response.CotizacionResponse;
import co.edu.uniquindio.sga.application.usecase.CotizarEstanciaUseCase;
import co.edu.uniquindio.sga.domain.valueobject.Dinero;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import co.edu.uniquindio.sga.domain.valueobject.IdentificacionApartamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
@Tag(name = "Cotizaciones", description = "Cotización previa del costo de alojamiento")
public class CotizacionController {

    private final CotizarEstanciaUseCase cotizarEstanciaUseCase;

    @PostMapping
    @Operation(summary = "Cotizar el costo total de una estancia proyectada")
    @ApiResponse(responseCode = "200", description = "Cotización generada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Apartamento no encontrado")
    public ResponseEntity<CotizacionResponse> cotizar(@Valid @RequestBody CotizarEstanciaRequest request) {
        
        Estancia estancia = new Estancia(request.fechaEntrada(), request.fechaSalida());
        
        Dinero total = cotizarEstanciaUseCase.ejecutar(
            new IdentificacionApartamento(request.identificacionSolar()), // Corregido: identificacionSolar()
            estancia,
            new ArrayList<>(), // Se pasa vacío para cotización simple
            0
        );

        CotizacionResponse response = new CotizacionResponse(
            request.identificacionSolar(), // Corregido: identificacionSolar()
            request.fechaEntrada().toString(),
            request.fechaSalida().toString(),
            estancia.noches(), // Corregido: noches() exacto como está en el record
            total.valor() // Nota: Si Dinero es record usa monto(), si es clase usa getMonto()
        );

        return ResponseEntity.ok(response);
    }
}