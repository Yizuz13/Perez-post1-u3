package com.universidad.confudes.certificados;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

/**
 * Generador de la estructura base del certificado en formato PDF simulado.
 */
@Component
public class GeneradorCertificadoPDF {

    public byte[] generar(SolicitudCertificado solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud de certificado no puede ser nula.");
        }
        String plantilla = String.format(
                "%%PDF-1.4\n[PDF-HEADER] CONF-UDES 2026 | Certificado de Asistencia | Evento: %s | Participante: %s (%s) | Asistencia: %.1f%%",
                solicitud.getEventoId(),
                solicitud.getNombreParticipante(),
                solicitud.getParticipanteId(),
                solicitud.getPorcentajeAsistencia() * 100
        );
        return plantilla.getBytes(StandardCharsets.UTF_8);
    }
}
