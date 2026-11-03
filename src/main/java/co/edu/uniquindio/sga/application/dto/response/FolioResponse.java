package co.edu.uniquindio.sga.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record FolioResponse(
    String codigoFolio,
    String codigoReserva,
    BigDecimal totalCargos,
    BigDecimal totalPagos,
    BigDecimal saldoPendiente,
    List<CargoResponse> cargos,
    List<PagoResponse> pagos
) {
    public record CargoResponse(String concepto, BigDecimal monto, String fecha) {}
    public record PagoResponse(String idPago, BigDecimal monto, String medioPago, String fecha) {}
}