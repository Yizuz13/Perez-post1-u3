package com.universidad.confudes.acceso;

/**
 * Mantiene el contexto de seguridad del usuario actual utilizando ThreadLocal.
 */
public class ContextoUsuario {

    private static final ThreadLocal<String> ROL_ACTUAL = new ThreadLocal<>();

    public static String rolActual() {
        return ROL_ACTUAL.get();
    }

    public static void establecerRol(String rol) {
        ROL_ACTUAL.set(rol);
    }

    public static void limpiar() {
        ROL_ACTUAL.remove();
    }
}
