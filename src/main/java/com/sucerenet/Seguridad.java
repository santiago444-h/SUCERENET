
package com.sucerenet;

import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {

    // GENERAR HASH DE UNA CONTRASEÑA
    public static String encriptarContrasena(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt(12));
    }

    // VERIFICAR CONTRASEÑA CON HASH
    public static boolean verificarContrasena(
            String contrasena, String hash) {

        if (contrasena == null || hash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(contrasena, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // IDENTIFICAR SI UNA CONTRASEÑA YA TIENE HASH BCRYPT
    public static boolean esHashBCrypt(String contrasena) {
        return contrasena != null
                && contrasena.matches(
                "^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");
    }
}
