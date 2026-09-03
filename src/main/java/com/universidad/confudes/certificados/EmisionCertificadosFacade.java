package com.universidad.confudes.certificados;

import org.springframework.stereotype.Service;

/**
 * Fachada (Facade) que simplifica y orquesta el subsistema complejo de emisión
 * de certificados digitales en ConfUDES (validación, renderizado PDF, firma digital y despacho).
 */
@Service
public class EmisionCertificadosFacade implements ServicioCertificados {

    private final ValidadorAsistencia validadorAsistencia;
    private final GeneradorCertificadoPDF generadorCertificadoPDF;
    private final FirmaDigitalService firmaDigitalService;
    private final EnvioCorreoService envioCorreoService;

    public EmisionCertificadosFacade(ValidadorAsistencia validadorAsistencia,
                                    GeneradorCertificadoPDF generadorCertificadoPDF,
                                    FirmaDigitalService firmaDigitalService,
                                    EnvioCorreoService envioCorreoService) {
        this.validadorAsistencia = validadorAsistencia;
        this.generadorCertificadoPDF = generadorCertificadoPDF;
        this.firmaDigitalService = firmaDigitalService;
        this.envioCorreoService = envioCorreoService;
    }

    @Override
    public byte[] emitir(SolicitudCertificado solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud de certificado no puede ser nula.");
        }

        // 1. Validación de asistencia mínima requerida (80% / 0.8)
        if (!validadorAsistencia.validarAsistenciaMinima(solicitud, 0.8)) {
            throw new IllegalStateException("El participante no cumple con el porcentaje mínimo de asistencia requerido (80%).");
        }

        // 2. Generación del documento PDF base a partir de la plantilla
        byte[] pdfBase = generadorCertificadoPDF.generar(solicitud);

        // 3. Apertura de sesión, firma digital institucional y cierre seguro de sesión
        byte[] pdfFirmado;
        firmaDigitalService.abrirSesion();
        try {
            pdfFirmado = firmaDigitalService.firmarDocumento(pdfBase);
        } finally {
            firmaDigitalService.cerrarSesion();
        }

        // 4. Adjunto y envío seguro por correo electrónico
        envioCorreoService.enviarCertificado(solicitud.getCorreo(), pdfFirmado);

        // 5. Retorno del documento emitido y firmado
        return pdfFirmado;
    }
}
