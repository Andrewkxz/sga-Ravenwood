package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.response.ApartamentoResponse;
import co.edu.uniquindio.sga.application.usecase.ConsultarApartamentosDisponiblesUseCase;
import co.edu.uniquindio.sga.domain.entity.Apartamento;
import co.edu.uniquindio.sga.domain.valueobject.Estancia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/apartamentos")
@Tag(name = "Apartamentos", description = "Consultas de disponibilidad de alojamientos")
public class ApartamentoController {

    private final ConsultarApartamentosDisponiblesUseCase consultarDisponiblesUseCase;

    public ApartamentoController(ConsultarApartamentosDisponiblesUseCase consultarDisponiblesUseCase) {
        this.consultarDisponiblesUseCase = consultarDisponiblesUseCase;
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Consultar apartamentos disponibles por rango de fechas")
    @ApiResponse(responseCode = "200", description = "Listado de apartamentos disponibles")
    @ApiResponse(responseCode = "400", description = "Fechas inválidas o parámetros incorrectos")
    public ResponseEntity<List<ApartamentoResponse>> consultarDisponibles(
            @RequestParam("fechaEntrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
            @RequestParam("fechaSalida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
            @RequestParam(name = "numeroPersonas", defaultValue = "1") int numeroPersonas,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        
        // 1. Instanciar el Value Object del dominio
        Estancia estancia = new Estancia(fechaEntrada, fechaSalida);

        // 2. Invocar el UseCase (retorna List<Apartamento> del dominio)
        List<Apartamento> entidades = consultarDisponiblesUseCase.ejecutar(estancia, numeroPersonas);

        // 3. Mapear usando los getters exactos de tu entidad de dominio Apartamento
        List<ApartamentoResponse> disponibles = entidades.stream()
                .map(apt -> new ApartamentoResponse(
                        apt.getIdentificacion().valor(),
                        apt.getNombre(),
                        apt.getDormitorios(),
                        apt.getCapacidad(),
                        apt.getEstadoOperativo().name(),
                        apt.estaActivo()
                ))
                .toList();

        // 4. Aplicar paginación de 10 por página según la rúbrica
        int desde = Math.min(page * size, disponibles.size());
        int hasta = Math.min(desde + size, disponibles.size());
        List<ApartamentoResponse> paginados = disponibles.subList(desde, hasta);
        
        return ResponseEntity.ok(paginados);
    }
}