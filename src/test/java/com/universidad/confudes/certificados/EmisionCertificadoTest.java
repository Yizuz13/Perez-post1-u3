package com.universidad.confudes.certificados;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Emisión de Certificados - Patrón Facade")
public class EmisionCertificadoTest {

    private EmisionCertificadosFacade emisionCertificadosFacade;
    private ValidadorAsistencia validadorAsistencia;
    private GeneradorCertificadoPDF generadorCertificadoPDF;
    private FirmaDigitalService firmaDigitalService;
    private EnvioCorreoService envioCorreoService;

    @BeforeEach
    void setUp() {
        validadorAsistencia = new ValidadorAsistencia();
        generadorCertificadoPDF = new GeneradorCertificadoPDF();
        firmaDigitalService = new FirmaDigitalService();
        envioCorreoService = new EnvioCorreoService();

        emisionCertificadosFacade = new EmisionCertificadosFacade(
                validadorAsistencia,
                generadorCertificadoPDF,
                firmaDigitalService,
                envioCorreoService
        );
    }

    @Test
    @DisplayName("Debe orquestar el flujo completo de emisión de certificado sin excepciones")
    void testEmisionCertificadoExitosa() {
        SolicitudCertificado solicitud = new SolicitudCertificado(
                "PART-001",
                "CONF-UDES-2026",
                "Jesús Adolfo Pérez",
                "jesus.perez@udes.edu.co",
                0.95
        );

        assertDoesNotThrow(() -> {
            byte[] resultado = emisionCertificadosFacade.emitir(solicitud);
            assertNotNull(resultado, "El certificado emitido no debe ser nulo");
            assertTrue(resultado.length > 0, "El certificado emitido debe contener bytes de datos");

            String contenido = new String(resultado, StandardCharsets.UTF_8);
            assertTrue(contenido.contains("Jesús Adolfo Pérez"), "Debe contener el nombre del participante");
            assertTrue(contenido.contains("[FIRMA-DIGITAL-INSTITUCIONAL-VALIDADA]"), "Debe contener la firma digital institucional");
        });

        // Verificar que la sesión de firma haya quedado cerrada tras la emisión (try-finally)
        assertFalse(firmaDigitalService.isSesionAbierta(), "La sesión de firma debe cerrarse tras emitir el certificado");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el participante no cumple con el 80% de asistencia")
    void testRechazoPorAsistenciaInsuficiente() {
        SolicitudCertificado solicitudInvalida = new SolicitudCertificado(
                "PART-002",
                "CONF-UDES-2026",
                "Carlos Gómez",
                "carlos.gomez@udes.edu.co",
                0.65 // Asistencia menor a 0.80
        );

        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            emisionCertificadosFacade.emitir(solicitudInvalida);
        });

        assertTrue(excepcion.getMessage().contains("80%"), "El mensaje de error debe indicar el requisito de asistencia");
    }

    @Test
    @DisplayName("Verificación por reflexión: ControladorCertificados debe tener exactamente 1 constructor con 1 parámetro")
    void testControladorCertificadosDesacopladoPorReflexion() {
        Constructor<?>[] constructores = ControladorCertificados.class.getDeclaredConstructors();
        assertEquals(1, constructores.length, "ControladorCertificados debe poseer exactamente 1 constructor");

        Constructor<?> constructor = constructores[0];
        Class<?>[] tiposParametros = constructor.getParameterTypes();
        assertEquals(1, tiposParametros.length, "El constructor debe recibir exactamente 1 parámetro (la interfaz ServicioCertificados)");
        assertEquals(ServicioCertificados.class, tiposParametros[0], "El parámetro del constructor debe ser de tipo ServicioCertificados");
    }
}
