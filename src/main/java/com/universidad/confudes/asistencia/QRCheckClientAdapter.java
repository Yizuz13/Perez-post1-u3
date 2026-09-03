package com.universidad.confudes.asistencia;

import com.universidad.confudes.externo.qrcheck.QRCheckClient;
import com.universidad.confudes.externo.qrcheck.QRCheckRequest;
import com.universidad.confudes.externo.qrcheck.QRCheckResponse;
import org.springframework.stereotype.Service;

/**
 * Adaptador que implementa el patrón estructural Adapter.
 * Permite que el sistema interno de asistencia se comunique con el cliente
 * externo legado QRCheckClient traduciendo tipos incompatibles y formatos de datos.
 */
@Service
public class QRCheckClientAdapter implements ServicioAsistencia {

    private final QRCheckClient qrCheckClient;

    public QRCheckClientAdapter(QRCheckClient qrCheckClient) {
        this.qrCheckClient = qrCheckClient;
    }

    @Override
    public ResultadoCheckIn registrarAsistencia(String eventoId, String participanteId, String credencialQR) {
        if (eventoId == null || eventoId.trim().isEmpty() || credencialQR == null || credencialQR.trim().isEmpty()) {
            return new ResultadoCheckIn(false, "Parámetros de check-in inválidos o ausentes");
        }

        long eventoIdAdaptado = adaptarEventoId(eventoId);
        if (eventoIdAdaptado <= 0) {
            return new ResultadoCheckIn(false, "ID de evento inválido en el subsistema legado");
        }

        String credencialAdaptada = adaptarCredencialQR(credencialQR);

        QRCheckRequest request = new QRCheckRequest(eventoIdAdaptado, credencialAdaptada);
        QRCheckResponse response = qrCheckClient.validar(request);

        boolean exitoso = response != null && response.getCodigoEstado() == 200;
        String detalle = response != null ? response.getMensaje() : "Error en el subsistema de check-in";

        return new ResultadoCheckIn(exitoso, detalle);
    }

    @Override
    public ResultadoCheckIn registrarAsistencia(String eventoId, String credencialQR) {
        return registrarAsistencia(eventoId, null, credencialQR);
    }

    /**
     * Transforma el identificador de evento de String a tipo primitivo long,
     * extrayendo dígitos numéricos o aplicando un hash positivo como alternativa.
     */
    private long adaptarEventoId(String eventoId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            return 0L;
        }
        String soloDigitos = eventoId.replaceAll("\\D+", "");
        if (!soloDigitos.isEmpty()) {
            try {
                long valor = Long.parseLong(soloDigitos);
                return valor;
            } catch (NumberFormatException ignored) {
                // Si excede el rango de Long, procede al hash
            }
        }
        long hash = Math.abs((long) eventoId.hashCode());
        return hash == 0 ? 1L : hash;
    }

    /**
     * Valida y formatea la credencial para garantizar el prefijo requerido 'QR-'.
     */
    private String adaptarCredencialQR(String credencialQR) {
        if (credencialQR == null) {
            return "";
        }
        String limpia = credencialQR.trim();
        if (limpia.startsWith("QR-")) {
            return limpia;
        }
        return "QR-" + limpia;
    }
}
