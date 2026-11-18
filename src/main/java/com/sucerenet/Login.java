
package com.sucerenet;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public Login() {
        setTitle("SECURENET - Inicio de sesión");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirInterfaz();
    }

    private void construirInterfaz() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 24, 22));
        panel.setLayout(null);

        JLabel titulo = new JLabel("SECURENET");
        titulo.setBounds(100, 35, 250, 40);
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        JLabel subtitulo = new JLabel("SISTEMA DE SEGURIDAD");
        subtitulo.setBounds(100, 75, 250, 25);
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitulo);

        JLabel lblCorreo = new JLabel("Correo electrónico");
        lblCorreo.setBounds(65, 125, 300, 25);
        lblCorreo.setForeground(Color.WHITE);
        panel.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(65, 150, 320, 35);
        panel.add(txtCorreo);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(65, 200, 300, 25);
        lblContrasena.setForeground(Color.WHITE);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(65, 225, 320, 35);
        panel.add(txtContrasena);

        btnIngresar = new JButton("INICIAR SESIÓN");
        btnIngresar.setBounds(65, 290, 320, 40);
        btnIngresar.setBackground(new Color(0, 180, 100));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        panel.add(btnIngresar);
// ACCIÓN DEL BOTÓN INICIAR SESIÓN
btnIngresar.addActionListener(e -> iniciarSesion());
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
    
    // VALIDAR INICIO DE SESIÓN
    private void iniciarSesion() {

        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, completa todos los campos."
            );
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        Usuario usuario = dao.autenticar(correo, contrasena);

       
if (usuario != null) {

            // REGISTRAR ALERTA DE INICIO DE SESIÓN
            Alerta alerta = new Alerta();
            alerta.setUsuarioId(usuario.getId());
            alerta.setTipo("Inicio de sesión");
            alerta.setDescripcion(
                    "El usuario inició sesión correctamente."
            );

            new AlertaDAO().insertar(alerta);

    JOptionPane.showMessageDialog(
            this,
            "Bienvenido a SECURENET, " + usuario.getNombre()
    );

    
if ("Administrador".equalsIgnoreCase(usuario.getRol())) {

    PanelAdministrador ventana =
            new PanelAdministrador(usuario);

    ventana.setVisible(true);

} else if ("Usuario".equalsIgnoreCase(usuario.getRol())) {


  PanelUsuario ventana = new PanelUsuario(usuario);
    ventana.setVisible(true);

}
 else {

        JOptionPane.showMessageDialog(
                this,
                "El rol de este usuario no está reconocido."
        );

    }

    this.dispose();


        } else {

            // BUSCAR SI EL CORREO PERTENECE A UN USUARIO
            Usuario usuarioIntento = dao.buscarPorCorreo(correo);

            // REGISTRAR INTENTO FALLIDO
            if (usuarioIntento != null) {

                Alerta alerta = new Alerta();
                alerta.setUsuarioId(usuarioIntento.getId());
                alerta.setTipo("Intento de acceso fallido");
                alerta.setDescripcion(
                        "Se intentó ingresar con una contraseña incorrecta."
                );

                new AlertaDAO().insertar(alerta);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Correo o contraseña incorrectos.",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
