package com.universidad.confudes.certificados.decoradores;

import com.universidad.confudes.certificados.ServicioCertificados;
import com.universidad.confudes.certificados.SolicitudCertificado;
import com.universidad.confudes.certificados.UtilidadesPDF;

/**
 * Decorador concreto que estampa una marca de agua institucional al certificado PDF.
 */
public class MarcaAguaDecorator extends CertificadoDecoratorBase {

    private final String textoMarca;

    public MarcaAguaDecorator(ServicioCertificados envoltorio) {
        this(envoltorio, "CONGRESO UDES 2026");
    }

    public MarcaAguaDecorator(ServicioCertificados envoltorio, String textoMarca) {
        super(envoltorio);
        this.textoMarca = (textoMarca != null && !textoMarca.trim().isEmpty()) ? textoMarca : "CONGRESO UDES 2026";
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        byte[] doc = super.emitir(solicitud);
        return UtilidadesPDF.aplicarMarcaDeAgua(doc, textoMarca);
    }
}
