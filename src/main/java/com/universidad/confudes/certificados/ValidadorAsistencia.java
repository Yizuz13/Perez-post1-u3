package com.universidad.confudes.certificados;

import org.springframework.stereotype.Component;

/**
 * Validador de cumplimiento de asistencia para la emisión de certificados.
 */
@Component
public class ValidadorAsistencia {

    private static final double UMBRAL_ASISTENCIA_MINIMA = 0.8;

    public boolean validarAsistenciaMinima(SolicitudCertificado solicitud) {
        return validarAsistenciaMinima(solicitud, UMBRAL_ASISTENCIA_MINIMA);
    }

    public boolean validarAsistenciaMinima(SolicitudCertificado solicitud, double porcentajeMinimo) {
        if (solicitud == null) {
            return false;
        }
        return solicitud.getPorcentajeAsistencia() >= porcentajeMinimo;
    }
}
