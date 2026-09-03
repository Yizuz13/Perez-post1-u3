package com.universidad.confudes.acceso;

import com.universidad.confudes.certificados.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Control de Acceso y Seguridad - Patrón Proxy")
public class AccesoDescargaMasivaTest {

    private ServicioCertificadosProxySeguridad proxySeguridad;
    private SolicitudCertificado solicitud;

    @BeforeEach
    void setUp() {
        ValidadorAsistencia validador = new ValidadorAsistencia();
        GeneradorCertificadoPDF generador = new GeneradorCertificadoPDF();
        FirmaDigitalService firma = new FirmaDigitalService();
        EnvioCorreoService correo = new EnvioCorreoService();

        ServicioCertificados servicioReal = new EmisionCertificadosFacade(validador, generador, firma, correo);
        proxySeguridad = new ServicioCertificadosProxySeguridad(servicioReal);

        solicitud = new SolicitudCertificado(
                "PART-001",
                "CONF-UDES-2026",
                "Jesús Adolfo Pérez",
                "jesus.perez@udes.edu.co",
                0.85
        );
    }

    @AfterEach
    void tearDown() {
        ContextoUsuario.limpiar();
    }

    @Test
    @DisplayName("Debe denegar acceso y lanzar SecurityException cuando el rol es PARTICIPANTE")
    void testAccesoDenegadoParaParticipante() {
        ContextoUsuario.establecerRol("PARTICIPANTE");

        SecurityException excepcion = assertThrows(SecurityException.class, () -> {
            proxySeguridad.emitir(solicitud);
        });

        assertEquals("Acceso restringido: Se requiere rol ORGANIZADOR o ADMIN.", excepcion.getMessage());
    }

    @Test
    @DisplayName("Debe denegar acceso cuando no hay ningún rol establecido en el contexto")
    void testAccesoDenegadoSinRol() {
        ContextoUsuario.limpiar();

        SecurityException excepcion = assertThrows(SecurityException.class, () -> {
            proxySeguridad.emitir(solicitud);
        });

        assertEquals("Acceso restringido: Se requiere rol ORGANIZADOR o ADMIN.", excepcion.getMessage());
    }

    @Test
    @DisplayName("Debe autorizar y permitir la ejecución completa cuando el rol es ORGANIZADOR")
    void testAccesoPermitidoParaOrganizador() {
        ContextoUsuario.establecerRol("ORGANIZADOR");

        assertDoesNotThrow(() -> {
            byte[] resultado = proxySeguridad.emitir(solicitud);
            assertNotNull(resultado, "El resultado no debe ser nulo para un organizador autorizado");
            assertTrue(resultado.length > 0, "El certificado debe contener bytes generados");
        });
    }

    @Test
    @DisplayName("Debe autorizar y permitir la ejecución completa cuando el rol es ADMIN")
    void testAccesoPermitidoParaAdmin() {
        ContextoUsuario.establecerRol("ADMIN");

        assertDoesNotThrow(() -> {
            byte[] resultado = proxySeguridad.emitir(solicitud);
            assertNotNull(resultado, "El resultado no debe ser nulo para un administrador");
            assertTrue(resultado.length > 0);
        });
    }
}
