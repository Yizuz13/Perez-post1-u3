package com.universidad.confudes.certificados;

import com.universidad.confudes.certificados.decoradores.CodigoQRDecorator;
import com.universidad.confudes.certificados.decoradores.MarcaAguaDecorator;
import com.universidad.confudes.certificados.decoradores.TraduccionInglesDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Decoradores Dinámicos de Certificados - Patrón Decorator")
public class MejorasCertificadoTest {

    private ServicioCertificados servicioBase;
    private SolicitudCertificado solicitud;

    @BeforeEach
    void setUp() {
        ValidadorAsistencia validador = new ValidadorAsistencia();
        GeneradorCertificadoPDF generador = new GeneradorCertificadoPDF();
        FirmaDigitalService firma = new FirmaDigitalService();
        EnvioCorreoService correo = new EnvioCorreoService();

        servicioBase = new EmisionCertificadosFacade(validador, generador, firma, correo);

        solicitud = new SolicitudCertificado(
                "PART-777",
                "CONF-UDES-2026",
                "Jesús Adolfo Pérez",
                "jesus.perez@udes.edu.co",
                0.90
        );
    }

    @Test
    @DisplayName("Emisión base sin decoradores")
    void testEmisionBase() {
        byte[] docBase = servicioBase.emitir(solicitud);
        assertNotNull(docBase);
        String contenido = new String(docBase, StandardCharsets.UTF_8);

        assertTrue(contenido.contains("[PDF-HEADER]"), "Debe contener el encabezado PDF base");
        assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"), "Debe contener la firma institucional");
        assertFalse(contenido.contains("[MARCA-AGUA:"), "No debe contener marca de agua");
        assertFalse(contenido.contains("[QR-VERIFICACION:"), "No debe contener QR de verificación");
        assertFalse(contenido.contains("[TRADUCCION-INGLES:"), "No debe contener traducción al inglés");
    }

    @Test
    @DisplayName("Emisión con mejora individual de Marca de Agua")
    void testMejoraIndividualMarcaAgua() {
        ServicioCertificados servicioDecorado = new MarcaAguaDecorator(servicioBase);
        byte[] resultado = servicioDecorado.emitir(solicitud);

        assertNotNull(resultado);
        String contenido = new String(resultado, StandardCharsets.UTF_8);

        assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"));
        assertTrue(contenido.contains("[MARCA-AGUA: CONGRESO UDES 2026]"), "Debe estampar la marca de agua requerida");
        assertFalse(contenido.contains("[QR-VERIFICACION:"));
        assertFalse(contenido.contains("[TRADUCCION-INGLES:"));
    }

    @Test
    @DisplayName("Emisión con mejora individual de Código QR")
    void testMejoraIndividualCodigoQR() {
        ServicioCertificados servicioDecorado = new CodigoQRDecorator(servicioBase);
        byte[] resultado = servicioDecorado.emitir(solicitud);

        assertNotNull(resultado);
        String contenido = new String(resultado, StandardCharsets.UTF_8);

        assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"));
        assertTrue(contenido.contains("[QR-VERIFICACION: https://confudes.udes.edu.co/validar/PART-777]"), "Debe insertar la URL del QR con el participante");
        assertFalse(contenido.contains("[MARCA-AGUA:"));
        assertFalse(contenido.contains("[TRADUCCION-INGLES:"));
    }

    @Test
    @DisplayName("Emisión con mejora individual de Traducción al Inglés")
    void testMejoraIndividualTraduccionIngles() {
        ServicioCertificados servicioDecorado = new TraduccionInglesDecorator(servicioBase);
        byte[] resultado = servicioDecorado.emitir(solicitud);

        assertNotNull(resultado);
        String contenido = new String(resultado, StandardCharsets.UTF_8);

        assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"));
        assertTrue(contenido.contains("[TRADUCCION-INGLES: Certificate of Attendance]"));
        assertFalse(contenido.contains("[MARCA-AGUA:"));
        assertFalse(contenido.contains("[QR-VERIFICACION:"));
    }

    @Test
    @DisplayName("Emisión combinada anidando los 3 decoradores concurrentemente sin crear clases nuevas")
    void testEmisionCombinadaTresDecoradores() {
        // Envoltorio encadenado: Base -> Marca de Agua -> Código QR -> Traducción al Inglés
        ServicioCertificados servicioTotalmenteDecorado = new TraduccionInglesDecorator(
                new CodigoQRDecorator(
                        new MarcaAguaDecorator(servicioBase)
                )
        );

        byte[] resultado = servicioTotalmenteDecorado.emitir(solicitud);
        assertNotNull(resultado);

        String contenido = new String(resultado, StandardCharsets.UTF_8);

        // Validar que contiene la base y todas las decoraciones acumuladas en el flujo
        assertTrue(contenido.contains("[PDF-HEADER]"), "Debe contener el encabezado PDF original");
        assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"), "Debe contener la firma institucional");
        assertTrue(contenido.contains("[MARCA-AGUA: CONGRESO UDES 2026]"), "Debe contener la marca de agua agregada");
        assertTrue(contenido.contains("[QR-VERIFICACION: https://confudes.udes.edu.co/validar/PART-777]"), "Debe contener el código QR insertado");
        assertTrue(contenido.contains("[TRADUCCION-INGLES: Certificate of Attendance]"), "Debe contener la traducción a inglés");
    }
}
