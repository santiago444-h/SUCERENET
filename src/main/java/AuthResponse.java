
package com.securenet;

/**
 * Clase que representa la respuesta enviada por el servicio web
 * después de realizar una operación de registro o autenticación.
 */
public class AuthResponse {

    // Indica si la operación fue exitosa o fallida.
    private boolean exitoso;

    // Contiene el mensaje descriptivo del resultado.
    private String mensaje;

    // Almacena el nombre del usuario cuando la operación es exitosa.
    private String usuario;

    /**
     * Constructor que inicializa los datos de la respuesta.
     */
    public AuthResponse(boolean exitoso, String mensaje, String usuario) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    // Permite consultar si la operación fue exitosa.
    public boolean isExitoso() {
        return exitoso;
    }

    // Permite obtener el mensaje de respuesta.
    public String getMensaje() {
        return mensaje;
    }

    // Permite obtener el nombre del usuario.
    public String getUsuario() {
        return usuario;
    }
}
