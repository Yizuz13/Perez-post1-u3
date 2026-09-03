package com.universidad.confudes.asistencia;

/**
 * Contrato del servicio interno de registro de asistencia en ConfUDES.
 */
public interface ServicioAsistencia {

    /**
     * Registra la asistencia especificando evento, participante y credencial QR.
     *
     * @param eventoId       Identificador del evento.
     * @param participanteId Identificador del participante.
     * @param credencialQR   Código o credencial QR escaneada.
     * @return ResultadoCheckIn con el estado de la operación.
     */
    ResultadoCheckIn registrarAsistencia(String eventoId, String participanteId, String credencialQR);

    /**
     * Registra la asistencia especificando únicamente evento y credencial QR.
     *
     * @param eventoId     Identificador del evento.
     * @param credencialQR Código o credencial QR escaneada.
     * @return ResultadoCheckIn con el estado de la operación.
     */
    default ResultadoCheckIn registrarAsistencia(String eventoId, String credencialQR) {
        return registrarAsistencia(eventoId, null, credencialQR);
    }

    /**
     * Alias compatible con operaciones de Check-In.
     *
     * @param eventoId     Identificador del evento.
     * @param credencialQR Código o credencial QR escaneada.
     * @return ResultadoCheckIn con el estado de la operación.
     */
    default ResultadoCheckIn registrarCheckIn(String eventoId, String credencialQR) {
        return registrarAsistencia(eventoId, null, credencialQR);
    }
}
