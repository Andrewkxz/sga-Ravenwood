package co.edu.uniquindio.sga.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record RegistrarPagoRequest(
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    BigDecimal monto,
    
    @NotNull(message = "El medio de pago es obligatorio")
    String medioPago
) {}