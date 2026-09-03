package com.universidad.confudes.acceso;

import com.universidad.confudes.certificados.ServicioCertificados;
import com.universidad.confudes.certificados.SolicitudCertificado;

/**
 * Proxy de Protección que restringe la emisión de certificados
 * exclusivamente a usuarios con privilegios autorizados (ORGANIZADOR o ADMIN),
 * abortando la ejecución antes de invocar operaciones costosas del servicio real.
 */
public class ServicioCertificadosProxySeguridad implements ServicioCertificados {

    private final ServicioCertificados servicioReal;

    public ServicioCertificadosProxySeguridad(ServicioCertificados servicioReal) {
        this.servicioReal = servicioReal;
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        String rol = ContextoUsuario.rolActual();
        if (rol != null && ("ORGANIZADOR".equalsIgnoreCase(rol.trim()) || "ADMIN".equalsIgnoreCase(rol.trim()))) {
            return servicioReal.emitir(solicitud);
        }
        throw new SecurityException("Acceso restringido: Se requiere rol ORGANIZADOR o ADMIN.");
    }
}
