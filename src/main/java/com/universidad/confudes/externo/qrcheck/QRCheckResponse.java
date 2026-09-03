package com.universidad.confudes.externo.qrcheck;

/**
 * Respuesta devuelta por el cliente externo QRCheck.
 */
public class QRCheckResponse {

    private final int codigoEstado;
    private final String mensaje;

    public QRCheckResponse(int codigoEstado, String mensaje) {
        this.codigoEstado = codigoEstado;
        this.mensaje = mensaje;
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public int getStatusCode() {
        return codigoEstado;
    }

    public String getMensaje() {
        return mensaje;
    }
}
