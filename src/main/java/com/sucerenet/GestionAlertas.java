
package com.sucerenet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionAlertas extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private final AlertaDAO dao = new AlertaDAO();

    public GestionAlertas() {

        setTitle("SECURENET - Gestión de alertas");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(20, 24, 22));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(
                "ALERTAS DE SEGURIDAD - TODOS LOS USUARIOS",
                SwingConstants.CENTER
        );
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 19));

        modelo = new DefaultTableModel(
                new String[]{"ID", "ID Usuario", "Tipo",
                    "Descripción", "Fecha"}, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setBackground(new Color(35, 42, 38));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(0, 160, 90));
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setBackground(new Color(0, 160, 90));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 13));

        btnActualizar.addActionListener(e -> cargarAlertas());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnActualizar, BorderLayout.SOUTH);

        add(panel);

        cargarAlertas();
    }

    private void cargarAlertas() {

        modelo.setRowCount(0);

        List<Alerta> alertas = dao.consultarTodas();

        for (Alerta a : alertas) {

            modelo.addRow(new Object[]{
                a.getId(),
                a.getUsuarioId(),
                a.getTipo(),
                a.getDescripcion(),
                a.getFecha()
            });
        }
    }
}
