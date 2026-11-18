
package com.sucerenet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    // REGISTRAR ALERTA
    public boolean insertar(Alerta alerta) {

        String sql = "INSERT INTO alertas "
                + "(usuario_id, tipo, descripcion) "
                + "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, alerta.getUsuarioId());
            ps.setString(2, alerta.getTipo());
            ps.setString(3, alerta.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al registrar alerta: "
                    + e.getMessage());
            return false;
        }
    }

    // CONSULTAR ALERTAS DE UN USUARIO
    public List<Alerta> consultarPorUsuario(int usuarioId) {

        List<Alerta> alertas = new ArrayList<>();

        String sql = "SELECT * FROM alertas "
                + "WHERE usuario_id = ? ORDER BY fecha DESC";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Alerta alerta = new Alerta();

                    alerta.setId(rs.getInt("id"));
                    alerta.setUsuarioId(rs.getInt("usuario_id"));
                    alerta.setTipo(rs.getString("tipo"));
                    alerta.setDescripcion(
                            rs.getString("descripcion"));
                    alerta.setFecha(rs.getTimestamp("fecha"));

                    alertas.add(alerta);
                }
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al consultar alertas: "
                    + e.getMessage());
        }

        return alertas;
    }
    // CONSULTAR TODAS LAS ALERTAS
public List<Alerta> consultarTodas() {

    List<Alerta> alertas = new ArrayList<>();

    String sql = "SELECT * FROM alertas ORDER BY fecha DESC";

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Alerta alerta = new Alerta();

            alerta.setId(rs.getInt("id"));
            alerta.setUsuarioId(rs.getInt("usuario_id"));
            alerta.setTipo(rs.getString("tipo"));
            alerta.setDescripcion(rs.getString("descripcion"));
            alerta.setFecha(rs.getTimestamp("fecha"));

            alertas.add(alerta);
        }

    } catch (SQLException | NullPointerException e) {
        System.out.println("Error al consultar todas las alertas: "
                + e.getMessage());
    }

    return alertas;
}
}
