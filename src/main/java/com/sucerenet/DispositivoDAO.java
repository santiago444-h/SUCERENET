
package com.sucerenet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DispositivoDAO {

    // REGISTRAR DISPOSITIVO
    public boolean insertar(Dispositivo dispositivo) {

        String sql = "INSERT INTO dispositivos "
                + "(usuario_id, nombre_dispositivo, marca, "
                + "modelo, imei, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, dispositivo.getUsuarioId());
            ps.setString(2, dispositivo.getNombreDispositivo());
            ps.setString(3, dispositivo.getMarca());
            ps.setString(4, dispositivo.getModelo());
            ps.setString(5, dispositivo.getImei());
            ps.setString(6, "Protegido");

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al registrar dispositivo: "
                    + e.getMessage());
            return false;
        }
    }

    // CONSULTAR DISPOSITIVOS DE UN USUARIO
    public List<Dispositivo> consultarPorUsuario(int usuarioId) {

        List<Dispositivo> dispositivos = new ArrayList<>();

        String sql = "SELECT * FROM dispositivos WHERE usuario_id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Dispositivo dispositivo = new Dispositivo();

                    dispositivo.setId(rs.getInt("id"));
                    dispositivo.setUsuarioId(
                            rs.getInt("usuario_id"));
                    dispositivo.setNombreDispositivo(
                            rs.getString("nombre_dispositivo"));
                    dispositivo.setMarca(rs.getString("marca"));
                    dispositivo.setModelo(rs.getString("modelo"));
                    dispositivo.setImei(rs.getString("imei"));
                    dispositivo.setEstado(rs.getString("estado"));

                    dispositivos.add(dispositivo);
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al consultar dispositivos: "
                    + e.getMessage());
        }

        return dispositivos;
    }
    // CONSULTAR TODOS LOS DISPOSITIVOS
public List<Dispositivo> consultarTodos() {

    List<Dispositivo> dispositivos = new ArrayList<>();

    String sql = "SELECT * FROM dispositivos ORDER BY id DESC";

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Dispositivo dispositivo = new Dispositivo();

            dispositivo.setId(rs.getInt("id"));
            dispositivo.setUsuarioId(rs.getInt("usuario_id"));
            dispositivo.setNombreDispositivo(
                    rs.getString("nombre_dispositivo"));
            dispositivo.setMarca(rs.getString("marca"));
            dispositivo.setModelo(rs.getString("modelo"));
            dispositivo.setImei(rs.getString("imei"));
            dispositivo.setEstado(rs.getString("estado"));

            dispositivos.add(dispositivo);
        }

    } catch (SQLException | NullPointerException e) {
        System.out.println("Error al consultar todos los dispositivos: "
                + e.getMessage());
    }

    return dispositivos;
}
}
