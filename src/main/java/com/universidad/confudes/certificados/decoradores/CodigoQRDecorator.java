package com.universidad.confudes.certificados.decoradores;

import com.universidad.confudes.certificados.ServicioCertificados;
import com.universidad.confudes.certificados.SolicitudCertificado;
import com.universidad.confudes.certificados.UtilidadesPDF;

/**
 * Decorador concreto que inserta un código QR de verificación de autenticidad en el certificado.
 */
public class CodigoQRDecorator extends CertificadoDecoratorBase {

    private final String urlBase;

    public CodigoQRDecorator(ServicioCertificados envoltorio) {
        this(envoltorio, "https://confudes.udes.edu.co/validar/");
    }

    public CodigoQRDecorator(ServicioCertificados envoltorio, String urlBase) {
        super(envoltorio);
        this.urlBase = (urlBase != null && !urlBase.trim().isEmpty()) ? urlBase : "https://confudes.udes.edu.co/validar/";
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        byte[] doc = super.emitir(solicitud);
        String idParticipante = (solicitud != null && solicitud.getParticipanteId() != null)
                ? solicitud.getParticipanteId()
                : "";
        String urlFinal;
        if (urlBase.endsWith("/")) {
            urlFinal = urlBase + idParticipante;
        } else {
            urlFinal = urlBase + "/" + idParticipante;
        }
        return UtilidadesPDF.insertarCodigoQR(doc, urlFinal);
    }
}
