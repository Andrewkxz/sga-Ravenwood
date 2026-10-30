package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.response.ApartamentoResponse;
import co.edu.uniquindio.sga.application.usecase.ConsultarApartamentosDisponiblesUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/apartamentos")
public class ApartamentoController {

    private final ConsultarApartamentosDisponiblesUseCase consultarDisponiblesUseCase;

    public ApartamentoController(ConsultarApartamentosDisponiblesUseCase consultarDisponiblesUseCase) {
        this.consultarDisponiblesUseCase = consultarDisponiblesUseCase;
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ApartamentoResponse>> consultarDisponibles(
            @RequestParam("fechaEntrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
            @RequestParam("fechaSalida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
            @RequestParam("numeroPersonas") int numeroPersonas) {
        
        List<ApartamentoResponse> disponibles = consultarDisponiblesUseCase.ejecutar(
                fechaEntrada, fechaSalida, numeroPersonas);
        
        return ResponseEntity.ok(disponibles);
    }
}