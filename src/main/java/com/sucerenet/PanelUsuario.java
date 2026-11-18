
package com.sucerenet;

import javax.swing.*;
import java.awt.*;

public class PanelUsuario extends JFrame {

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JPasswordField txtNuevaContrasena;

    private Usuario usuario;

    public PanelUsuario(Usuario usuario) {
        this.usuario = usuario;

        setTitle("SECURENET - Panel de Usuario");
        setSize(550, 610);
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
        titulo.setBounds(100, 20, 350, 40);
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        JLabel subtitulo = new JLabel("MI PERFIL");
        subtitulo.setBounds(50, 75, 450, 25);
        subtitulo.setForeground(new Color(0, 220, 120));
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(subtitulo);

        agregarEtiqueta(panel, "Nombre:", 115);
        txtNombre = agregarCampo(panel, usuario.getNombre(), 115);

        agregarEtiqueta(panel, "Correo:", 165);
        agregarDato(panel, usuario.getCorreo(), 165);

        agregarEtiqueta(panel, "Teléfono:", 215);
        txtTelefono = agregarCampo(panel, usuario.getTelefono(), 215);

        agregarEtiqueta(panel, "Rol:", 265);
        agregarDato(panel, usuario.getRol(), 265);

        agregarEtiqueta(panel, "Nueva contraseña:", 315);
        txtNuevaContrasena = new JPasswordField();
        txtNuevaContrasena.setBounds(190, 315, 290, 30);
        panel.add(txtNuevaContrasena);

        JLabel aviso = new JLabel(
                "Deja la contraseña vacía si no deseas cambiarla."
        );
        aviso.setBounds(50, 355, 450, 20);
        aviso.setForeground(Color.LIGHT_GRAY);
        aviso.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(aviso);

        JButton btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnGuardar.setBounds(145, 395, 260, 40);
        btnGuardar.setBackground(new Color(0, 180, 100));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);

        btnGuardar.addActionListener(e -> guardarCambios());
        panel.add(btnGuardar);

        JButton btnDispositivos = new JButton("MIS DISPOSITIVOS");
        btnDispositivos.setBounds(145, 440, 260, 35);
        btnDispositivos.setBackground(new Color(0, 140, 85));
        btnDispositivos.setForeground(Color.WHITE);
        btnDispositivos.setFocusPainted(false);

        btnDispositivos.addActionListener(e -> {
            new MisDispositivos(usuario).setVisible(true);
        });

        panel.add(btnDispositivos);

        JButton btnAlertas = new JButton("ALERTAS DE SEGURIDAD");
        btnAlertas.setBounds(145, 480, 260, 35);
        btnAlertas.setBackground(new Color(0, 140, 85));
        btnAlertas.setForeground(Color.WHITE);
        btnAlertas.setFocusPainted(false);

        btnAlertas.addActionListener(e -> {
            new MisAlertas(usuario).setVisible(true);
        });

        panel.add(btnAlertas);

        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.setBounds(145, 530, 260, 35);
        btnCerrar.setBackground(new Color(45, 55, 50));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);

        btnCerrar.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        panel.add(btnCerrar);

        add(panel);
    }

    private void agregarEtiqueta(
            JPanel panel, String texto, int y) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setBounds(50, y, 140, 30);
        etiqueta.setForeground(new Color(0, 220, 120));
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(etiqueta);
    }

    private JTextField agregarCampo(
            JPanel panel, String valor, int y) {

        JTextField campo = new JTextField(valor == null ? "" : valor);
        campo.setBounds(190, y, 290, 30);
        panel.add(campo);
        return campo;
    }

    private void agregarDato(
            JPanel panel, String valor, int y) {

        JLabel dato = new JLabel(valor == null ? "" : valor);
        dato.setBounds(190, y, 290, 30);
        dato.setForeground(Color.WHITE);
        dato.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(dato);
    }

    private void guardarCambios() {

        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String contrasena = new String(
                txtNuevaContrasena.getPassword()
        );

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Completa el nombre y el teléfono."
            );
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(
                    this, "El teléfono debe tener 10 números."
            );
            return;
        }

        if (!contrasena.isEmpty() && contrasena.length() < 8) {
            JOptionPane.showMessageDialog(
                    this, "La contraseña debe tener mínimo 8 caracteres."
            );
            return;
        }

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.actualizarPerfilUsuario(usuario)) {

            JOptionPane.showMessageDialog(
                    this, "¡Tu perfil se actualizó correctamente!"
            );

            txtNuevaContrasena.setText("");

        } else {
            JOptionPane.showMessageDialog(
                    this, "No fue posible actualizar el perfil."
            );
        }
    }
}
