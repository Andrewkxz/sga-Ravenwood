package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.request.RegistrarSalidaRequest;
import co.edu.uniquindio.sga.application.dto.response.ReservaDetalleResponse;
import co.edu.uniquindio.sga.application.usecase.DeclararNoShowUseCase;
import co.edu.uniquindio.sga.application.usecase.RegistrarLlegadaUseCase;
import co.edu.uniquindio.sga.application.usecase.RegistrarSalidaUseCase;
import co.edu.uniquindio.sga.domain.entity.Reserva;
import co.edu.uniquindio.sga.domain.valueobject.CodigoReserva;
import co.edu.uniquindio.sga.infrastructure.rest.mapper.ReservaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/operaciones")
@RequiredArgsConstructor
@Tag(name = "Operaciones Diarias", description = "Check-in, Check-out y declaración de No-Show")
public class OperacionDiariaController {

    private final RegistrarLlegadaUseCase registrarLlegadaUseCase;
    private final RegistrarSalidaUseCase registrarSalidaUseCase;
    private final DeclararNoShowUseCase declararNoShowUseCase;
    private final ReservaMapper mapper;

    @PostMapping("/reservas/{codigo}/llegada")
    @Operation(summary = "Registrar la llegada del huésped (Check-In)")
    @ApiResponse(responseCode = "200", description = "Llegada registrada correctamente")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    @ApiResponse(responseCode = "409", description = "Regla de negocio impedida")
    public ResponseEntity<ReservaDetalleResponse> registrarLlegada(@PathVariable String codigo) {
        Reserva reserva = registrarLlegadaUseCase.ejecutar(new CodigoReserva(codigo), LocalDateTime.now());
        return ResponseEntity.ok(mapper.aDetalle(reserva));
    }

    @PostMapping("/reservas/{codigo}/salida")
    @Operation(summary = "Registrar la salida del huésped (Check-Out)")
    @ApiResponse(responseCode = "200", description = "Salida registrada correctamente")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    @ApiResponse(responseCode = "409", description = "Saldo pendiente o impedimento de negocio")
    public ResponseEntity<ReservaDetalleResponse> registrarSalida(
            @PathVariable String codigo,
            @Valid @RequestBody(required = false) RegistrarSalidaRequest request) {
        
        // Si el request es nulo (no se envió cuerpo), autorizacion es null
        String autorizacion = (request != null) ? request.autorizacionCierre() : null;
        
        // Firma real de tu UseCase: (CodigoReserva, autorizacion, LocalDate)
        Reserva reserva = registrarSalidaUseCase.ejecutar(new CodigoReserva(codigo), autorizacion, LocalDate.now());
        
        return ResponseEntity.ok(mapper.aDetalle(reserva));
    }

    @PostMapping("/reservas/{codigo}/no-show")
    @Operation(summary = "Declarar No-Show a una reserva que no se presentó")
    @ApiResponse(responseCode = "200", description = "No-Show declarado")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    @ApiResponse(responseCode = "409", description = "Transición inválida")
    public ResponseEntity<ReservaDetalleResponse> declararNoShow(@PathVariable String codigo) {
        Reserva reserva = declararNoShowUseCase.ejecutar(new CodigoReserva(codigo), LocalDateTime.now());
        return ResponseEntity.ok(mapper.aDetalle(reserva));
    }
}