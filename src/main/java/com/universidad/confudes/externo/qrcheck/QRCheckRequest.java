package com.universidad.confudes.externo.qrcheck;

/**
 * Solicitud de validación esperada por el cliente externo QRCheck.
 */
public class QRCheckRequest {

    private final long eventoId;
    private final String credencialQR;

    public QRCheckRequest(long eventoId, String credencialQR) {
        this.eventoId = eventoId;
        this.credencialQR = credencialQR;
    }

    public long getEventoId() {
        return eventoId;
    }

    public String getCredencialQR() {
        return credencialQR;
    }
}
