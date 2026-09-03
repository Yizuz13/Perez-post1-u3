package com.universidad.confudes.certificados;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

/**
 * Servicio de firma digital institucional para certificados en ConfUDES.
 */
@Component
public class FirmaDigitalService {

    private boolean sesionAbierta = false;

    public void abrirSesion() {
        this.sesionAbierta = true;
    }

    public void cerrarSesion() {
        this.sesionAbierta = false;
    }

    public byte[] firmarDocumento(byte[] documento) {
        if (!sesionAbierta) {
            throw new IllegalStateException("No hay una sesión de firma digital activa.");
        }
        if (documento == null) {
            throw new IllegalArgumentException("El documento a firmar no puede ser nulo.");
        }
        String contenido = new String(documento, StandardCharsets.UTF_8);
        String firmado = contenido + " | [FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]";
        return firmado.getBytes(StandardCharsets.UTF_8);
    }

    public boolean isSesionAbierta() {
        return sesionAbierta;
    }
}
