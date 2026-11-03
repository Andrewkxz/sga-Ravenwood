package co.edu.uniquindio.sga.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearReservaExternaRequest(
    @NotBlank(message = "El identificador externo es obligatorio")
    String identificadorExterno,
    
    @NotBlank(message = "El canal de origen es obligatorio")
    String canalOrigen
) {}