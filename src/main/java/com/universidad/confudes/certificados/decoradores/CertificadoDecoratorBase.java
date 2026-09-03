package com.universidad.confudes.certificados.decoradores;

import com.universidad.confudes.certificados.ServicioCertificados;
import com.universidad.confudes.certificados.SolicitudCertificado;

/**
 * Clase base abstracta del patrón Decorator para el enriquecimiento dinámico
 * de certificados en ConfUDES.
 */
public abstract class CertificadoDecoratorBase implements ServicioCertificados {

    protected final ServicioCertificados envoltorio;

    public CertificadoDecoratorBase(ServicioCertificados envoltorio) {
        this.envoltorio = envoltorio;
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        return envoltorio.emitir(solicitud);
    }
}
