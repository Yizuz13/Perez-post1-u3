package com.universidad.confudes.certificados;

/**
 * DTO que encapsula los datos requeridos para la emisión de un certificado.
 */
public class SolicitudCertificado {

    private final String participanteId;
    private final String eventoId;
    private final String nombreParticipante;
    private final String correo;
    private final double porcentajeAsistencia;

    public SolicitudCertificado(String participanteId, String eventoId, String nombreParticipante, String correo, double porcentajeAsistencia) {
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.nombreParticipante = nombreParticipante;
        this.correo = correo;
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    /**
     * Constructor sobrecargado para compatibilidad con firmas basadas en horas asistidas.
     */
    public SolicitudCertificado(String participanteId, String nombreParticipante, String eventoId, String curso, String correo, double asistidas, double totales) {
        this(participanteId, eventoId, nombreParticipante, correo, totales > 0 ? (asistidas / totales) : 1.0);
    }

    public String getParticipanteId() {
        return participanteId;
    }

    public String getEventoId() {
        return eventoId;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public String getCorreo() {
        return correo;
    }

    public double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }
}
