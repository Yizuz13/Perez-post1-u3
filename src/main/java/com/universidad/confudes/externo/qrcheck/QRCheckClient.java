package com.universidad.confudes.externo.qrcheck;

import org.springframework.stereotype.Component;

/**
 * Cliente legado/externo para validación de credenciales QR.
 */
@Component
public class QRCheckClient {

    public QRCheckResponse validar(QRCheckRequest request) {
        if (request == null) {
            return new QRCheckResponse(400, "Solicitud nula");
        }
        if (request.getEventoId() <= 0) {
            return new QRCheckResponse(401, "ID de evento inválido en el subsistema legado");
        }
        if (request.getCredencialQR() == null || !request.getCredencialQR().startsWith("QR-")) {
            return new QRCheckResponse(401, "Formato de credencial QR inválido. Debe iniciar con 'QR-'");
        }
        if (request.getCredencialQR().trim().length() <= 3) {
            return new QRCheckResponse(401, "Credencial QR vacía o incompleta");
        }
        return new QRCheckResponse(200, "Acceso concedido para el evento " + request.getEventoId() + " con credencial " + request.getCredencialQR());
    }

    public QRCheckResponse validarAcceso(QRCheckRequest request) {
        return validar(request);
    }
}
