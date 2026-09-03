package com.universidad.confudes.certificados;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST desacoplado gracias al patrón Facade.
 * Depende exclusivamente de la abstracción ServicioCertificados.
 */
@RestController
@RequestMapping("/api/certificados")
public class ControladorCertificados {

    private final ServicioCertificados servicioCertificados;

    public ControladorCertificados(ServicioCertificados servicioCertificados) {
        this.servicioCertificados = servicioCertificados;
    }

    @PostMapping("/emitir")
    public ResponseEntity<byte[]> emitir(@RequestBody SolicitudCertificado solicitud) {
        byte[] pdf = servicioCertificados.emitir(solicitud);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
