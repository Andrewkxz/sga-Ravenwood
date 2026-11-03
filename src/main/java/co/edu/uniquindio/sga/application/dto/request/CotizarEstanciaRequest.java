package co.edu.uniquindio.sga.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CotizarEstanciaRequest(
    @NotBlank(message = "La identificación del solar es obligatoria")
    String identificacionSolar,
    
    @NotNull(message = "La fecha de entrada es obligatoria")
    LocalDate fechaEntrada,
    
    @NotNull(message = "La fecha de salida es obligatoria")
    LocalDate fechaSalida,
    
    @NotEmpty(message = "Debe haber al menos un ocupante")
    List<OcupanteRequest> ocupantes
) {}