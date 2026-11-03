package co.edu.uniquindio.sga.application.dto.response;

import java.math.BigDecimal;

public record CotizacionResponse(
    String identificacionApartamento,
    String fechaEntrada,
    String fechaSalida,
    int numeroNoches,
    BigDecimal precioTotal
) {}