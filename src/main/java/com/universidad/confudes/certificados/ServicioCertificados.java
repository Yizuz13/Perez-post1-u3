package com.universidad.confudes.certificados;

/**
 * Contrato principal para la emisión de certificados en ConfUDES.
 */
public interface ServicioCertificados {

    /**
     * Emite un certificado digital en formato PDF a partir de una solicitud.
     *
     * @param solicitud Datos del participante y evento para el certificado.
     * @return Arreglo de bytes del documento PDF generado y firmado.
     */
    byte[] emitir(SolicitudCertificado solicitud);
}
