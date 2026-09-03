package com.universidad.confudes.asistencia;

import com.universidad.confudes.externo.qrcheck.QRCheckClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de Integración y Adaptación - Check-in (Adapter Pattern)")
public class CheckInIntegracionTest {

    private QRCheckClientAdapter qrCheckClientAdapter;

    @BeforeEach
    void setUp() {
        QRCheckClient qrCheckClient = new QRCheckClient();
        qrCheckClientAdapter = new QRCheckClientAdapter(qrCheckClient);
    }

    @Test
    @DisplayName("Debe registrar check-in exitoso cuando la credencial y evento son válidos")
    void testCheckInExitosoConCredencialValida() {
        ResultadoCheckIn resultado = qrCheckClientAdapter.registrarAsistencia("EVENT-501", "QR-abc123");
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.isExitoso(), "El check-in debería ser exitoso");
        assertTrue(resultado.getDetalle().contains("Acceso concedido"), "El detalle debe indicar acceso concedido");
    }

    @Test
    @DisplayName("Debe adaptar la credencial asegurando el prefijo QR- si viene sin él")
    void testCheckInAdaptandoFormatoCredencial() {
        ResultadoCheckIn resultado = qrCheckClientAdapter.registrarAsistencia("2026", "asistente456");
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertTrue(resultado.isExitoso(), "Debe procesar con éxito adaptando el prefijo");
        assertTrue(resultado.getDetalle().contains("QR-asistente456"), "La respuesta debe contener la credencial adaptada con QR-");
    }

    @Test
    @DisplayName("Debe rechazar el check-in cuando la credencial o el evento son inválidos")
    void testCheckInRechazadoCredencialInvalida() {
        ResultadoCheckIn resultadoNulo = qrCheckClientAdapter.registrarAsistencia(null, null);
        assertNotNull(resultadoNulo);
        assertFalse(resultadoNulo.isExitoso(), "Debe rechazar parámetros nulos");

        ResultadoCheckIn resultadoVacio = qrCheckClientAdapter.registrarAsistencia("0", "");
        assertNotNull(resultadoVacio);
        assertFalse(resultadoVacio.isExitoso(), "Debe rechazar credencial vacía");

        ResultadoCheckIn resultadoInvalido = qrCheckClientAdapter.registrarAsistencia("-10", "QR-");
        assertNotNull(resultadoInvalido);
        assertFalse(resultadoInvalido.isExitoso(), "Debe rechazar credencial incompleta");
    }
}
