package co.edu.uniquindio.sga.application.dto.response;

public record ApartamentoDetalleResponse(
    String identificacion,
    String estadoOperativo,
    int capacidadMaxima,
    int camasDisponibles,
    String caracteristicas
) {}