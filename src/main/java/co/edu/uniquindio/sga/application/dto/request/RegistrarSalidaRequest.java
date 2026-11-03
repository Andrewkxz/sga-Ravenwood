package co.edu.uniquindio.sga.application.dto.request;

/** DTO de entrada de la API para registrar la salida del grupo (Guía 07) */
public record RegistrarSalidaRequest(
        // Opcional: la autorización del administrador solo se requiere si el folio presenta saldo pendiente (RN-17)
        String autorizacionCierre
) {
}