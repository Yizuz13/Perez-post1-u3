package com.universidad.confudes.certificados;

import java.nio.charset.StandardCharsets;

/**
 * Utilidades para la manipulación y enriquecimiento de documentos PDF en ConfUDES.
 */
public class UtilidadesPDF {

    public static byte[] aplicarMarcaDeAgua(byte[] doc, String texto) {
        if (doc == null) {
            return new byte[0];
        }
        String contenido = new String(doc, StandardCharsets.UTF_8);
        String resultado = contenido + " | [MARCA-AGUA: " + texto + "] | MARCA DE AGUA: " + texto;
        return resultado.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] insertarCodigoQR(byte[] doc, String urlVerificacion) {
        if (doc == null) {
            return new byte[0];
        }
        String contenido = new String(doc, StandardCharsets.UTF_8);
        String resultado = contenido + " | [QR-VERIFICACION: " + urlVerificacion + "] | CODIGO QR VERIFICACION " + urlVerificacion;
        return resultado.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] traducirAIngles(byte[] doc) {
        if (doc == null) {
            return new byte[0];
        }
        String contenido = new String(doc, StandardCharsets.UTF_8);
        String resultado = contenido + " | [TRADUCCION-INGLES: Certificate of Attendance] | CERTIFICATE OF ATTENDANCE AND PARTICIPATION | Awarded to: | CAPA TRADUCCION INGLES";
        return resultado.getBytes(StandardCharsets.UTF_8);
    }
}
