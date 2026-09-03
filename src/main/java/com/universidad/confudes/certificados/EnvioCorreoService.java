package com.universidad.confudes.certificados;

import org.springframework.stereotype.Component;

/**
 * Servicio de despacho de correos electrónicos con certificados adjuntos.
 */
@Component
public class EnvioCorreoService {

    public void enviarCertificado(String correo, byte[] certificadoPDF) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo destinatario es inválido.");
        }
        if (certificadoPDF == null || certificadoPDF.length == 0) {
            throw new IllegalArgumentException("El certificado adjunto no puede estar vacío.");
        }
        // Simulación de envío de correo institucional
        System.out.println("Certificado despachado exitosamente a: " + correo);
    }
}
