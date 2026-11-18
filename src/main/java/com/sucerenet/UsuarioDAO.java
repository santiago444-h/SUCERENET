
package com.sucerenet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // INSERTAR USUARIO
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios "
                + "(nombre, correo, telefono, contrasena, rol) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

ps.setString(1, usuario.getNombre());
ps.setString(2, usuario.getCorreo());
ps.setString(3, usuario.getTelefono());

// Proteger contraseña con BCrypt
String contrasenaHash = Seguridad.encriptarContrasena(
        usuario.getContrasena()
);

ps.setString(4, contrasenaHash);
ps.setString(5, usuario.getRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // CONSULTAR TODOS LOS USUARIOS
    public List<Usuario> consultar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRol(rs.getString("rol"));

                usuarios.add(usuario);
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }

        return usuarios;
    }

    // VERIFICAR SI EL CORREO EXISTE
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar correo: " + e.getMessage());
        }

        return false;
    }

    // VERIFICAR CORREO DE OTRO USUARIO
    public boolean existeCorreoOtroUsuario(String correo, int id) {
        String sql = "SELECT COUNT(*) FROM usuarios "
                + "WHERE correo = ? AND id <> ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar correo: " + e.getMessage());
        }

        return false;
    }

    

 // ACTUALIZAR USUARIO
public boolean actualizar(Usuario usuario) {
    String sql = "UPDATE usuarios SET nombre=?, correo=?, "
            + "telefono=?, "
            + "contrasena=CASE WHEN ? = '' THEN contrasena ELSE ? END, "
            + "rol=? WHERE id=?";

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getCorreo());
        ps.setString(3, usuario.getTelefono());

        String nuevaContrasena = usuario.getContrasena();

        String contrasenaHash = nuevaContrasena.isEmpty()
                ? ""
                : Seguridad.encriptarContrasena(nuevaContrasena);

        ps.setString(4, contrasenaHash);
        ps.setString(5, contrasenaHash);

        ps.setString(6, usuario.getRol());
        ps.setInt(7, usuario.getId());

        return ps.executeUpdate() > 0;

    } catch (SQLException | NullPointerException e) {
        System.out.println("Error al actualizar: " + e.getMessage());
        return false;
    }
}


    // ELIMINAR USUARIO
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
    
    // BUSCAR USUARIO POR CORREO PARA INICIAR SESIÓN
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setRol(rs.getString("rol"));

                    return usuario;
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }
    
    // AUTENTICAR USUARIO Y MIGRAR CONTRASEÑAS ANTIGUAS
    public Usuario autenticar(String correo, String contrasena) {

        if (correo == null || contrasena == null) {
            return null;
        }

        Usuario usuario = buscarPorCorreo(correo);

        if (usuario == null) {
            return null;
        }

        String contrasenaGuardada = usuario.getContrasena();

        // Si ya tiene BCrypt, verificar directamente
        if (Seguridad.esHashBCrypt(contrasenaGuardada)) {

            if (Seguridad.verificarContrasena(
                    contrasena, contrasenaGuardada)) {
                return usuario;
            }

            return null;
        }

        // Si es una contraseña antigua, comparar texto original
        if (contrasena.equals(contrasenaGuardada)) {

            String nuevoHash =
                    Seguridad.encriptarContrasena(contrasena);

            String sql = "UPDATE usuarios SET contrasena=? WHERE id=?";

            try (Connection conexion = ConexionDB.conectar();
                 PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setString(1, nuevoHash);
                ps.setInt(2, usuario.getId());

                if (ps.executeUpdate() > 0) {
                    usuario.setContrasena(nuevoHash);
                    return usuario;
                }

            } catch (SQLException | NullPointerException e) {
                System.out.println(
                        "Error al proteger contraseña antigua: "
                        + e.getMessage()
                );
            }
        }

        return null;
    }
    
    // ACTUALIZAR PERFIL DEL USUARIO NORMAL
    public boolean actualizarPerfilUsuario(Usuario usuario) {

        String sql = "UPDATE usuarios SET nombre=?, telefono=?, "
                + "contrasena=CASE WHEN ? = '' THEN contrasena ELSE ? END "
                + "WHERE id=? AND LOWER(rol)='usuario'";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getTelefono());

            String nuevaContrasena = usuario.getContrasena();

            String hash = (nuevaContrasena == null
                    || nuevaContrasena.isEmpty())
                    ? ""
                    : Seguridad.encriptarContrasena(nuevaContrasena);

            ps.setString(3, hash);
            ps.setString(4, hash);
            ps.setInt(5, usuario.getId());

            boolean actualizado = ps.executeUpdate() > 0;

            if (actualizado) {
                usuario.setNombre(usuario.getNombre());
                usuario.setTelefono(usuario.getTelefono());

                if (!hash.isEmpty()) {
                    usuario.setContrasena(hash);
                }
            }

            return actualizado;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al actualizar perfil: "
                    + e.getMessage());
            return false;
        }
    }
}
