package com.universidad.confudes.certificados.decoradores;

import com.universidad.confudes.certificados.ServicioCertificados;
import com.universidad.confudes.certificados.SolicitudCertificado;
import com.universidad.confudes.certificados.UtilidadesPDF;

/**
 * Decorador concreto que añade la traducción al idioma inglés del certificado.
 */
public class TraduccionInglesDecorator extends CertificadoDecoratorBase {

    public TraduccionInglesDecorator(ServicioCertificados envoltorio) {
        super(envoltorio);
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        byte[] doc = super.emitir(solicitud);
        return UtilidadesPDF.traducirAIngles(doc);
    }
}
