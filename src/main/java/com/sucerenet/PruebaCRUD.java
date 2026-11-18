
package com.sucerenet;

public class PruebaCRUD {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        if (!dao.consultar().isEmpty()) {

            Usuario usuario = dao.consultar().get(0);

            System.out.println("Usuario a eliminar: "
                    + usuario.getNombre());

            if (dao.eliminar(usuario.getId())) {
                System.out.println(
                        "Usuario eliminado correctamente."
                );
            } else {
                System.out.println(
                        "No se pudo eliminar el usuario."
                );
            }

        } else {
            System.out.println("No hay usuarios para eliminar.");
        }
    }
}
