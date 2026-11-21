
package com.securenet;

import com.sucerenet.Usuario;
import com.sucerenet.UsuarioDAO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST de SUCERENET.
 * Gestiona el registro de usuarios y la autenticación mediante
 * solicitudes HTTP.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Objeto encargado de realizar las operaciones con la base de datos.
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Comprueba que el servicio web esté disponible.
     * Método: GET
     * Ruta: /api/auth/estado
     */
    @GetMapping("/estado")
    public ResponseEntity<Map<String, String>> estado() {

        // Devuelve un mensaje indicando que el servicio está activo.
        return ResponseEntity.ok(
            Map.of("mensaje", "Servicio web SUCERENET funcionando")
        );
    }

    /**
     * Registra un usuario nuevo en el sistema.
     * Método: POST
     * Ruta: /api/auth/registro
     *
     * Recibe nombre, correo, teléfono y contraseña en formato JSON.
     */
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(
            @RequestBody Map<String, String> datos) {

        // Obtiene los datos enviados en la solicitud.
        String nombre = datos.get("nombre");
        String correo = datos.get("correo");
        String telefono = datos.get("telefono");
        String contrasena = datos.get("contrasena");

        // Comprueba que todos los campos obligatorios estén completos.
        if (nombre == null || nombre.isBlank()
                || correo == null || correo.isBlank()
                || telefono == null || telefono.isBlank()
                || contrasena == null || contrasena.isBlank()) {

            return ResponseEntity.badRequest().body(
                new AuthResponse(false, "Todos los campos son obligatorios", null)
            );
        }

        // Verifica que el correo no esté registrado previamente.
        if (usuarioDAO.existeCorreo(correo)) {
            return ResponseEntity.badRequest().body(
                new AuthResponse(false, "El correo ya está registrado", null)
            );
        }

        // Crea el objeto usuario con la información recibida.
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);
        usuario.setRol("Usuario");

        // Guarda el usuario y devuelve el resultado de la operación.
        if (usuarioDAO.insertar(usuario)) {
            return ResponseEntity.ok(
                new AuthResponse(true, "Registro exitoso", nombre)
            );
        }

        // Informa si ocurrió un error al guardar el usuario.
        return ResponseEntity.internalServerError().body(
            new AuthResponse(false, "No fue posible registrar el usuario", null)
        );
    }

    /**
     * Verifica las credenciales de un usuario registrado.
     * Método: POST
     * Ruta: /api/auth/login
     *
     * Recibe correo y contraseña en formato JSON.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(
            @RequestBody Map<String, String> datos) {

        // Obtiene el correo y la contraseña enviados por el cliente.
        String correo = datos.get("correo");
        String contrasena = datos.get("contrasena");

        // Valida que las credenciales no estén vacías.
        if (correo == null || correo.isBlank()
                || contrasena == null || contrasena.isBlank()) {

            return ResponseEntity.badRequest().body(
                new AuthResponse(false, "Ingrese correo y contraseña", null)
            );
        }

        // Consulta el usuario y comprueba sus credenciales.
        Usuario usuario = usuarioDAO.autenticar(correo, contrasena);

        // Si las credenciales son correctas, permite el acceso.
        if (usuario != null) {
            return ResponseEntity.ok(
                new AuthResponse(true, "Inicio de sesión exitoso",
                        usuario.getNombre())
            );
        }

        // Si los datos son incorrectos, devuelve el código HTTP 401.
        return ResponseEntity.status(401).body(
            new AuthResponse(false, "Correo o contraseña incorrectos", null)
        );
    }
}
