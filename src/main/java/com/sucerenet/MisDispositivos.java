
package com.sucerenet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MisDispositivos extends JFrame {

    private Usuario usuario;
    private DispositivoDAO dao = new DispositivoDAO();

    private JTextField txtNombre;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtImei;

    private JTable tabla;
    private DefaultTableModel modelo;

    public MisDispositivos(Usuario usuario) {

        this.usuario = usuario;

        setTitle("SECURENET - Mis Dispositivos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construirInterfaz();
        cargarDispositivos();
    }

    private void construirInterfaz() {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 24, 22));
        panel.setLayout(null);

        JLabel titulo = new JLabel("MIS DISPOSITIVOS");
        titulo.setBounds(200, 20, 400, 40);
        titulo.setForeground(new Color(0, 220, 120));
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo);

        agregarEtiqueta(panel, "Nombre:", 85);
        txtNombre = agregarCampo(panel, 85);

        agregarEtiqueta(panel, "Marca:", 130);
        txtMarca = agregarCampo(panel, 130);

        agregarEtiqueta(panel, "Modelo:", 175);
        txtModelo = agregarCampo(panel, 175);

        agregarEtiqueta(panel, "IMEI:", 220);
        txtImei = agregarCampo(panel, 220);

        JButton btnRegistrar = new JButton("REGISTRAR DISPOSITIVO");
        btnRegistrar.setBounds(250, 270, 300, 38);
        btnRegistrar.setBackground(new Color(0, 180, 100));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrarDispositivo());
        panel.add(btnRegistrar);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Marca",
                    "Modelo", "IMEI", "Estado"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setBackground(new Color(35, 42, 38));
        tabla.setForeground(Color.WHITE);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(40, 330, 700, 170);
        panel.add(scroll);

        JButton btnActualizar = new JButton("ACTUALIZAR LISTA");
        btnActualizar.setBounds(250, 515, 300, 35);
        btnActualizar.setBackground(new Color(0, 140, 85));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDispositivos());
        panel.add(btnActualizar);

        add(panel);
    }

    private void agregarEtiqueta(
            JPanel panel, String texto, int y) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setBounds(100, y, 100, 30);
        etiqueta.setForeground(new Color(0, 220, 120));
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(etiqueta);
    }

    private JTextField agregarCampo(JPanel panel, int y) {

        JTextField campo = new JTextField();
        campo.setBounds(200, y, 500, 30);
        panel.add(campo);
        return campo;
    }

    private void registrarDispositivo() {

        String nombre = txtNombre.getText().trim();
        String marca = txtMarca.getText().trim();
        String modeloTexto = txtModelo.getText().trim();
        String imei = txtImei.getText().trim();

        if (nombre.isEmpty() || marca.isEmpty()
                || modeloTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Completa el nombre, la marca y el modelo."
            );
            return;
        }

        if (!imei.isEmpty() && !imei.matches("\\d{15}")) {
            JOptionPane.showMessageDialog(
                    this, "El IMEI debe contener 15 dígitos."
            );
            return;
        }

        Dispositivo dispositivo = new Dispositivo();

        dispositivo.setUsuarioId(usuario.getId());
        dispositivo.setNombreDispositivo(nombre);
        dispositivo.setMarca(marca);
        dispositivo.setModelo(modeloTexto);
        dispositivo.setImei(imei);

        if (dao.insertar(dispositivo)) {

            // REGISTRAR ALERTA DE NUEVO DISPOSITIVO
            Alerta alerta = new Alerta();
            alerta.setUsuarioId(usuario.getId());
            alerta.setTipo("Nuevo dispositivo");
            alerta.setDescripcion(
                    "Se registró el dispositivo: "
                    + nombre + " - " + marca + " " + modeloTexto
            );

            new AlertaDAO().insertar(alerta);

            JOptionPane.showMessageDialog(
                    this, "¡Dispositivo registrado correctamente!"
            );

            txtNombre.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            txtImei.setText("");

            cargarDispositivos();

        } else {
            JOptionPane.showMessageDialog(
                    this, "No fue posible registrar el dispositivo."
            );
        }
    }

    private void cargarDispositivos() {

        modelo.setRowCount(0);

        for (Dispositivo dispositivo
                : dao.consultarPorUsuario(usuario.getId())) {

            modelo.addRow(new Object[]{
                dispositivo.getId(),
                dispositivo.getNombreDispositivo(),
                dispositivo.getMarca(),
                dispositivo.getModelo(),
                dispositivo.getImei(),
                dispositivo.getEstado()
            });
        }
    }
}

