package co.edu.uniquindio.sga.application.dto.response;

public record ApartamentoResponse(
    String identificacion,
    String nombre,
    int dormitorios,
    int capacidad,
    String estadoOperativo,
    boolean activo
) {}