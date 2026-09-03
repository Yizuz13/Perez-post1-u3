package com.universidad.confudes.asistencia;

import java.time.LocalDateTime;

/**
 * Representa el resultado de una operación de Check-in en el sistema de asistencia.
 */
public class ResultadoCheckIn {

    private final boolean exitoso;
    private final String detalle;
    private final LocalDateTime fechaRegistro;

    public ResultadoCheckIn(boolean exitoso, String detalle) {
        this(exitoso, detalle, LocalDateTime.now());
    }

    public ResultadoCheckIn(boolean exitoso, String detalle, LocalDateTime fechaRegistro) {
        this.exitoso = exitoso;
        this.detalle = detalle;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDateTime.now();
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getMensaje() {
        return detalle;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String toString() {
        return "ResultadoCheckIn{" +
                "exitoso=" + exitoso +
                ", detalle='" + detalle + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
