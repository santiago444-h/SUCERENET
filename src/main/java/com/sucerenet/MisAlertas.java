
package com.sucerenet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class MisAlertas extends JFrame {

    private Usuario usuario;
    private AlertaDAO dao = new AlertaDAO();
    private JTable tabla;
    private DefaultTableModel modelo;

    public MisAlertas(Usuario usuario) {

        this.usuario = usuario;

        setTitle("SECURENET - Alertas de Seguridad");
        setSize(750, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirInterfaz();
        cargarAlertas();
    }

    private void construirInterfaz() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 24, 22));
        panel.setLayout(null);

        JLabel titulo = new JLabel("ALERTAS DE SEGURIDAD");
        titulo.setBounds(100, 25, 550, 40);
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Descripción", "Fecha"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setBackground(new Color(35, 42, 38));
        tabla.setForeground(Color.WHITE);
        tabla.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(25, 100, 685, 250);
        panel.add(scroll);

        JButton btnActualizar = new JButton("ACTUALIZAR ALERTAS");
        btnActualizar.setBounds(235, 380, 280, 40);
        btnActualizar.setBackground(new Color(0, 180, 100));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarAlertas());
        panel.add(btnActualizar);

        add(panel);
    }

    private void cargarAlertas() {

        modelo.setRowCount(0);

        SimpleDateFormat formato =
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Alerta alerta : dao.consultarPorUsuario(usuario.getId())) {

            String fecha = alerta.getFecha() == null
                    ? ""
                    : formato.format(alerta.getFecha());

            modelo.addRow(new Object[]{
                alerta.getId(),
                alerta.getTipo(),
                alerta.getDescripcion(),
                fecha
            });
        }
    }
}
