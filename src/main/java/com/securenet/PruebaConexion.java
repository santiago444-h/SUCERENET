
package com.sucerenet;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        Connection conexion = ConexionDB.conectar();

        if (conexion != null) {
            System.out.println("SUCERENET conectado correctamente.");

            try {
                conexion.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar: " + e.getMessage());
            }

        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }
    }
}