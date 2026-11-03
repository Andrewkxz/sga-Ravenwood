package co.edu.uniquindio.sga.infrastructure.rest;

import co.edu.uniquindio.sga.application.dto.response.FolioResponse;
import co.edu.uniquindio.sga.application.usecase.ConsultarFolioUseCase;
import co.edu.uniquindio.sga.domain.entity.Folio;
import co.edu.uniquindio.sga.domain.valueobject.CodigoReserva;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folios")
@RequiredArgsConstructor
@Tag(name = "Folios", description = "Gestión de estado de cuenta, cargos y pagos del alojamiento")
public class FolioController {

    private final ConsultarFolioUseCase consultarFolioUseCase;

    @GetMapping("/{codigoReserva}")
    @Operation(summary = "Consultar el folio de una reserva por su código")
    @ApiResponse(responseCode = "200", description = "Detalle del folio")
    @ApiResponse(responseCode = "404", description = "El folio no existe")
    public ResponseEntity<FolioResponse> consultarFolio(@PathVariable String codigoReserva) {
        
        Folio folio = consultarFolioUseCase.ejecutar(new CodigoReserva(codigoReserva));
        
        FolioResponse response = new FolioResponse(
            folio.getCodigo().valor(),
            folio.getReserva().valor(),
            folio.totalCargos().valor(),
            folio.totalPagos().valor(),
            folio.saldo().valor(),
            folio.getCargos().stream()
                .map(c -> new FolioResponse.CargoResponse(
                    c.concepto().toString(),
                    c.valor().valor(),
                    c.fecha().toString()
                ))
                .toList(),
            folio.getPagos().stream()
                .map(p -> new FolioResponse.PagoResponse(
                    p.getId().valor(),
                    p.getMonto().valor(),
                    p.getMedio().name(),
                    p.getFecha().toString()
                ))
                .toList()
        );
        
        return ResponseEntity.ok(response);
    }
}