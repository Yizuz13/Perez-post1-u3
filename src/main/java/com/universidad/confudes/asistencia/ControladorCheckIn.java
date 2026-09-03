package com.universidad.confudes.asistencia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones de registro de asistencia (Check-in).
 */
@RestController
@RequestMapping("/api/asistencia")
public class ControladorCheckIn {

    private final ServicioAsistencia servicioAsistencia;

    public ControladorCheckIn(ServicioAsistencia servicioAsistencia) {
        this.servicioAsistencia = servicioAsistencia;
    }

    @PostMapping("/checkin")
    public ResponseEntity<ResultadoCheckIn> checkIn(@RequestParam String eventoId, @RequestParam String credencialQR) {
        ResultadoCheckIn resultado = servicioAsistencia.registrarAsistencia(eventoId, credencialQR);
        if (resultado.isExitoso()) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }

    public ResultadoCheckIn registrar(String eventoId, String credencialQR) {
        return servicioAsistencia.registrarAsistencia(eventoId, credencialQR);
    }
}
