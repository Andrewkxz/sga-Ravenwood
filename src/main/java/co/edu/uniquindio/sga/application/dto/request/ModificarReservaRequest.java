package co.edu.uniquindio.sga.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/** 
 * DTO para modificar una reserva activa (Guía 07, Tabla 5.9).
 * Contiene los mismos campos que CrearReservaRequest, excepto canalOrigen (que nunca cambia).
 */
public record ModificarReservaRequest(
        @NotBlank(message = "La identificación del apartamento es obligatoria")
        String identificacionApartamento,

        @NotNull(message = "La fecha de entrada es obligatoria")
        LocalDate fechaEntrada,

        @NotNull(message = "La fecha de salida es obligatoria")
        LocalDate fechaSalida,

        LocalTime horaEstimadaLlegada,

        @Min(value = 0, message = "La cantidad de mascotas no puede ser negativa")
        int cantidadMascotas,

        @NotNull(message = "La reserva debe tener titular")
        @Valid
        OcupanteRequest titular,

        @NotEmpty(message = "La reserva debe tener al menos un ocupante")
        @Valid
        List<OcupanteRequest> ocupantes
) {
}