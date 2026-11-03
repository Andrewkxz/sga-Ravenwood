package co.edu.uniquindio.sga.infrastructure.rest;

<<<<<<< HEAD
import co.edu.uniquindio.sga.application.dto.response.ApartamentoResponse;
import co.edu.uniquindio.sga.application.usecase.ConsultarApartamentosDisponiblesUseCase;
import org.springframework.format.annotation.DateTimeFormat;
=======
import co.edu.uniquindio.sga.application.dto.response.ApartamentoDetalleResponse;
>>>>>>> Andrewkxz
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
<<<<<<< HEAD
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
=======
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
>>>>>>> Andrewkxz
    }
}