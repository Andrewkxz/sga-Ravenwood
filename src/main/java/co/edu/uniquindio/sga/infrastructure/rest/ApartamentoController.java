package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.response.ApartamentoDetalleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/solares")
public class ApartamentoController {

    // Dependencias a los UseCases inyectadas por constructor...

    @GetMapping("/disponibles")
    public ResponseEntity<?> consultarSolaresDisponibles(
            @RequestParam LocalDate fechaEntrada,
            @RequestParam LocalDate fechaSalida,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // Invocar ConsultarSolaresDisponiblesUseCase
        return ResponseEntity.ok().build(); 
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<ApartamentoDetalleResponse> consultarSolar(@PathVariable String identificacion) {
        // Invocar ConsultarSolarUseCase
        ApartamentoDetalleResponse response = new ApartamentoDetalleResponse(identificacion, "PREPARADO", 6, 3, "Solar Estándar");
        return ResponseEntity.ok(response);
    }
}