/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sucerenet;

/**
 *
 * @author pc40
 */
public class GestionUsuarios extends javax.swing.JFrame {
   // Administrador de usuarios de SUCERENET
private final UsuarioDAO dao = new UsuarioDAO();
private javax.swing.JTextField txtNombre;
private javax.swing.JTextField txtCorreo;
private javax.swing.JTextField txtTelefono;
private javax.swing.JPasswordField txtContrasena;
private javax.swing.JComboBox<String> cbRol;
private javax.swing.JTable tablaUsuarios;
private javax.swing.table.DefaultTableModel modelo;
private javax.swing.JTextField txtBuscar;
private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> ordenador;
// Constructor de la ventana
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionUsuarios.class.getName());

    /**
     * Creates new form GestionUsuarios
     */
    public GestionUsuarios() {
    initComponents();

    setTitle("SECURENET - Gestión de usuarios");
    setSize(1000, 650);
    setLocationRelativeTo(null);
    setResizable(false);

    construirInterfaz();
    cargarUsuarios();
}
// Registrar un nuevo usuario
// Registrar un nuevo usuario
private void registrarUsuario() {
    if (txtNombre.getText().trim().isEmpty()
        || txtCorreo.getText().trim().isEmpty()
        || txtTelefono.getText().trim().isEmpty()
        || new String(txtContrasena.getPassword()).isEmpty()) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Por favor, completa todos los campos.",
        "Datos incompletos",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );
    return;
}
    if (!txtCorreo.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Ingresa un correo electrónico válido.",
        "Correo incorrecto",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );
    return;
}
    if (dao.existeCorreo(txtCorreo.getText().trim())) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Este correo electrónico ya está registrado.",
        "Correo duplicado",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );
    return;
}
    if (!txtTelefono.getText().trim().matches("\\d{10}")) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "El teléfono debe contener exactamente 10 números.",
        "Teléfono incorrecto",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );
    return;
}
    Usuario usuario = new Usuario(
        txtNombre.getText(),
        txtCorreo.getText(),
        txtTelefono.getText(),
        new String(txtContrasena.getPassword()),
        cbRol.getSelectedItem().toString()
    );

    if (dao.insertar(usuario)) {
        javax.swing.JOptionPane.showMessageDialog(
            this, "Usuario registrado correctamente."
        );
        cargarUsuarios();
        limpiarCampos();
    } else {
        javax.swing.JOptionPane.showMessageDialog(
            this, "No se pudo registrar el usuario."
        );
    }
}

// Limpiar los campos del formulario
private void limpiarCampos() {
    txtNombre.setText("");
    txtCorreo.setText("");
    txtTelefono.setText("");
    txtContrasena.setText("");
    cbRol.setSelectedIndex(0);
}
// Actualizar los datos de un usuario
private void actualizarUsuario() {

    int filaVista = tablaUsuarios.getSelectedRow();

int fila = filaVista == -1 ? -1 :
        tablaUsuarios.convertRowIndexToModel(filaVista);

    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this, "Selecciona un usuario de la tabla."
        );
        return;
    }

    int id = (int) modelo.getValueAt(fila, 0);

    Usuario usuario = new Usuario(
        txtNombre.getText(),
        txtCorreo.getText(),
        txtTelefono.getText(),
        new String(txtContrasena.getPassword()),
        cbRol.getSelectedItem().toString()
    );

usuario.setId(id);

if (dao.existeCorreoOtroUsuario(
        txtCorreo.getText().trim(), id)) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Este correo ya pertenece a otro usuario.",
        "Correo duplicado",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );
    return;
}

if (dao.actualizar(usuario)) {
        javax.swing.JOptionPane.showMessageDialog(
            this, "Usuario actualizado correctamente."
        );
        cargarUsuarios();
        limpiarCampos();
    } else {
        javax.swing.JOptionPane.showMessageDialog(
            this, "No se pudo actualizar el usuario."
        );
    }
}
private void eliminarUsuario() {

    int filaVista = tablaUsuarios.getSelectedRow();

int fila = filaVista == -1 ? -1 :
        tablaUsuarios.convertRowIndexToModel(filaVista);

    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this, "Selecciona un usuario para eliminar."
        );
        return;
    }

    int id = (int) modelo.getValueAt(fila, 0);

    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "¿Estás seguro de eliminar este usuario?",
        "Confirmar eliminación",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        if (dao.eliminar(id)) {
            javax.swing.JOptionPane.showMessageDialog(
                this, "Usuario eliminado correctamente."
            );
            cargarUsuarios();
            limpiarCampos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                this, "No se pudo eliminar el usuario."
            );
        }
    }
}
    
// Construir la interfaz gráfica de SUCERENET
private void construirInterfaz() {

    javax.swing.JPanel principal =
            new javax.swing.JPanel(new java.awt.BorderLayout(15, 15));

    principal.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)
    );

    principal.setBackground(new java.awt.Color(20, 25, 22));

    // Encabezado
    javax.swing.JLabel titulo = new javax.swing.JLabel(
            "SECURENET - GESTIÓN DE USUARIOS"
    );

titulo.setForeground(new java.awt.Color(0, 230, 118));
titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 26));
titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    principal.add(titulo, java.awt.BorderLayout.NORTH);

    // Formulario
    javax.swing.JPanel formulario = new javax.swing.JPanel(
            new java.awt.GridLayout(5, 2, 10, 10)
    );

    formulario.setBackground(new java.awt.Color(30, 38, 33));
formulario.setBorder(
    javax.swing.BorderFactory.createTitledBorder(
        javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(0, 230, 118)
        ),
        "DATOS DEL USUARIO",
        javax.swing.border.TitledBorder.LEFT,
        javax.swing.border.TitledBorder.TOP,
        new java.awt.Font("Arial", java.awt.Font.BOLD, 14),
        new java.awt.Color(0, 230, 118)
    )
);
    txtNombre = new javax.swing.JTextField();
    txtCorreo = new javax.swing.JTextField();
    txtTelefono = new javax.swing.JTextField();
    txtContrasena = new javax.swing.JPasswordField();
javax.swing.JButton btnMostrar = new javax.swing.JButton("Mostrar");
btnMostrar.setBackground(new java.awt.Color(0, 170, 90));
btnMostrar.setForeground(java.awt.Color.WHITE);
btnMostrar.setFocusPainted(false);
btnMostrar.addActionListener(e -> {
    if (txtContrasena.getEchoChar() == '\u2022') {
        txtContrasena.setEchoChar((char) 0);
        btnMostrar.setText("Ocultar");
    } else {
        txtContrasena.setEchoChar('\u2022');
        btnMostrar.setText("Mostrar");
    }
});
    cbRol = new javax.swing.JComboBox<>(
            new String[]{"Administrador", "Usuario"}
    );
java.awt.Font fuenteCampos =
        new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);

txtNombre.setFont(fuenteCampos);
txtCorreo.setFont(fuenteCampos);
txtTelefono.setFont(fuenteCampos);
txtContrasena.setFont(fuenteCampos);
cbRol.setFont(fuenteCampos);
    javax.swing.JLabel lblNombre = new javax.swing.JLabel("Nombre:");
lblNombre.setForeground(java.awt.Color.WHITE);
lblNombre.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
formulario.add(lblNombre);
    formulario.add(txtNombre);

   javax.swing.JLabel lblCorreo = new javax.swing.JLabel("Correo:");
lblCorreo.setForeground(java.awt.Color.WHITE);
lblCorreo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
formulario.add(lblCorreo);
formulario.add(txtCorreo);
javax.swing.JLabel lblTelefono = new javax.swing.JLabel("Teléfono:");
lblTelefono.setForeground(java.awt.Color.WHITE);
lblTelefono.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
formulario.add(lblTelefono);
formulario.add(txtTelefono);
javax.swing.JLabel lblContrasena = new javax.swing.JLabel("Contraseña:");
lblContrasena.setForeground(java.awt.Color.WHITE);
lblContrasena.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
formulario.add(lblContrasena);
javax.swing.JPanel panelContrasena = new javax.swing.JPanel(
        new java.awt.BorderLayout(5, 0)
);

panelContrasena.setBackground(new java.awt.Color(20, 25, 22));
btnMostrar.setPreferredSize(new java.awt.Dimension(90, 28));

panelContrasena.add(txtContrasena, java.awt.BorderLayout.CENTER);
panelContrasena.add(btnMostrar, java.awt.BorderLayout.EAST);

formulario.add(panelContrasena);
javax.swing.JLabel lblRol = new javax.swing.JLabel("Rol:");
lblRol.setForeground(java.awt.Color.WHITE);
lblRol.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
formulario.add(lblRol);
formulario.add(cbRol);
    // Tabla de usuarios
    modelo = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Nombre", "Correo", "Teléfono", "Rol"}, 0
    );

    tablaUsuarios = new javax.swing.JTable(modelo);
    ordenador = new javax.swing.table.TableRowSorter<>(modelo);
tablaUsuarios.setRowSorter(ordenador);
    tablaUsuarios.setRowHeight(28);
tablaUsuarios.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
tablaUsuarios.getTableHeader().setFont(
    new java.awt.Font("Arial", java.awt.Font.BOLD, 14)
);
tablaUsuarios.getTableHeader().setBackground(
    new java.awt.Color(0, 120, 65)
);
tablaUsuarios.getTableHeader().setForeground(
    java.awt.Color.WHITE
);
tablaUsuarios.setSelectionBackground(
    new java.awt.Color(0, 120, 65)
);
tablaUsuarios.setSelectionForeground(java.awt.Color.WHITE);
tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {

        int filaVista = tablaUsuarios.getSelectedRow();

        if (filaVista != -1) {

            int fila = tablaUsuarios.convertRowIndexToModel(filaVista);

            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtCorreo.setText(modelo.getValueAt(fila, 2).toString());
            txtTelefono.setText(modelo.getValueAt(fila, 3).toString());
            cbRol.setSelectedItem(modelo.getValueAt(fila, 4).toString());
        }
    }
});
    javax.swing.JScrollPane scroll =
            new javax.swing.JScrollPane(tablaUsuarios);

    // Panel central
    javax.swing.JPanel centro = new javax.swing.JPanel(
            new java.awt.BorderLayout(10, 10)
    );
javax.swing.JPanel panelBusqueda = new javax.swing.JPanel(
        new java.awt.FlowLayout(java.awt.FlowLayout.LEFT)
);

panelBusqueda.setBackground(new java.awt.Color(20, 25, 22));

javax.swing.JLabel lblBuscar = new javax.swing.JLabel("Buscar usuario:");
lblBuscar.setForeground(java.awt.Color.WHITE);
lblBuscar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));

txtBuscar = new javax.swing.JTextField(25);
txtBuscar.getDocument().addDocumentListener(
    new javax.swing.event.DocumentListener() {

        private void filtrar() {
            String texto = txtBuscar.getText();

            if (texto.trim().isEmpty()) {
                ordenador.setRowFilter(null);
            } else {
                ordenador.setRowFilter(
                    javax.swing.RowFilter.regexFilter(
                        "(?i)" + java.util.regex.Pattern.quote(texto),
                        1, 2
                    )
                );
            }
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }
    }
);
panelBusqueda.add(lblBuscar);
panelBusqueda.add(txtBuscar);
    centro.setBackground(new java.awt.Color(20, 25, 22));
   javax.swing.JPanel panelTabla = new javax.swing.JPanel(
        new java.awt.BorderLayout(5, 5)
);

panelTabla.setBackground(new java.awt.Color(20, 25, 22));
panelTabla.add(panelBusqueda, java.awt.BorderLayout.NORTH);
panelTabla.add(scroll, java.awt.BorderLayout.CENTER);

centro.add(formulario, java.awt.BorderLayout.NORTH);
centro.add(panelTabla, java.awt.BorderLayout.CENTER);

    principal.add(centro, java.awt.BorderLayout.CENTER);

    // Botones
    javax.swing.JPanel botones = new javax.swing.JPanel();

    botones.setBackground(new java.awt.Color(20, 25, 22));
botones.setBorder(
    javax.swing.BorderFactory.createEmptyBorder(10, 5, 5, 5)
);
    javax.swing.JButton btnRegistrar =
            new javax.swing.JButton("Registrar");
btnRegistrar.addActionListener(e -> registrarUsuario());
    javax.swing.JButton btnActualizar =
            new javax.swing.JButton("Actualizar");
btnActualizar.addActionListener(e -> actualizarUsuario());
    javax.swing.JButton btnEliminar =
            new javax.swing.JButton("Eliminar");
btnEliminar.addActionListener(e -> eliminarUsuario());
    javax.swing.JButton btnLimpiar =
            new javax.swing.JButton("Limpiar");
btnLimpiar.addActionListener(e -> limpiarCampos());
java.awt.Color verde = new java.awt.Color(0, 170, 90);
java.awt.Color textoBlanco = java.awt.Color.WHITE;

for (javax.swing.JButton boton :
        new javax.swing.JButton[]{
            btnRegistrar, btnActualizar, btnEliminar, btnLimpiar
        }) {
    boton.setBackground(verde);
    boton.setForeground(textoBlanco);
    boton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);
    boton.setPreferredSize(new java.awt.Dimension(130, 38));
}
    botones.add(btnRegistrar);
    botones.add(btnActualizar);
    botones.add(btnEliminar);
    botones.add(btnLimpiar);

    principal.add(botones, java.awt.BorderLayout.SOUTH);

    setContentPane(principal);
}
// Cargar usuarios desde la base de datos
private void cargarUsuarios() {
    modelo.setRowCount(0);

    for (Usuario usuario : dao.consultar()) {
        modelo.addRow(new Object[]{
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getTelefono(),
            usuario.getRol()
        });
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GestionUsuarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
