
package com.sucerenet;

import javax.swing.*;
import java.awt.*;

public class PanelAdministrador extends JFrame {

    private Usuario usuario;

    public PanelAdministrador(Usuario usuario) {

        this.usuario = usuario;

        setTitle("SECURENET - Panel Administrador");
        setSize(650, 520);
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
        titulo.setBounds(100, 25, 450, 45);
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        JLabel bienvenida = new JLabel(
                "Bienvenido, administrador " + usuario.getNombre()
        );
        bienvenida.setBounds(50, 85, 550, 35);
        bienvenida.setForeground(Color.WHITE);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 17));
        bienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(bienvenida);

        JLabel subtitulo = new JLabel("CENTRO DE ADMINISTRACIÓN");
        subtitulo.setBounds(50, 145, 550, 30);
        subtitulo.setForeground(new Color(0, 220, 120));
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitulo);

        JButton btnUsuarios = crearBoton(
                "GESTIÓN DE USUARIOS", 195
        );

        btnUsuarios.addActionListener(e -> {
            new GestionUsuarios().setVisible(true);
        });

        panel.add(btnUsuarios);

        JButton btnDispositivos = crearBoton(
                "GESTIÓN DE DISPOSITIVOS", 260
        );

        btnDispositivos.addActionListener(e -> {
    new GestionDispositivos().setVisible(true);
});

        panel.add(btnDispositivos);

        JButton btnAlertas = crearBoton(
                "ALERTAS DE SEGURIDAD", 325
        );

       btnAlertas.addActionListener(e -> {
    new GestionAlertas().setVisible(true);
});

        panel.add(btnAlertas);

        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.setBounds(180, 410, 290, 40);
        btnCerrar.setBackground(new Color(45, 55, 50));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCerrar.setFocusPainted(false);

        btnCerrar.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        panel.add(btnCerrar);

        add(panel);
    }

    private JButton crearBoton(String texto, int y) {

        JButton boton = new JButton(texto);
        boton.setBounds(150, y, 350, 45);
        boton.setBackground(new Color(0, 160, 90));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);

        return boton;
    }
}
