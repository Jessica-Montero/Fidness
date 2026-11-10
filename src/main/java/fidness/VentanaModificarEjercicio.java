
/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class VentanaModificarEjercicio extends JFrame {

    private Administrador administrador;
    private Repositorio repo;
    private Ejercicio ejercicio;
    private Runnable alCerrar;

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextArea txtInstrucciones;
    private JComboBox<TipoEjercicio> cmbTipo;

    public VentanaModificarEjercicio(
            Administrador administrador,
            Repositorio repo,
            Ejercicio ejercicio,
            Runnable alCerrar) {

        this.administrador = administrador;
        this.repo = repo;
        this.ejercicio = ejercicio;
        this.alCerrar = alCerrar;

        setTitle("FIDNESS - Modificar ejercicio");
        setSize(1050, 700);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        construirVentana();
        cargarDatosEjercicio();
    }

    private void construirVentana() {

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Estilo.BLANCO);
        
        // Barra superior degradada
        JPanel barraSuperior = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint degradado = new GradientPaint(
                        0, 0, Estilo.MORADO,
                        getWidth(), 0, Estilo.NARANJA);

                g2.setPaint(degradado);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        barraSuperior.setPreferredSize(new Dimension(950, 18));

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel(
                new BorderLayout(10, 20));

        panelIzquierdo.setBackground(Estilo.BLANCO);
         panelIzquierdo.setBorder(
                BorderFactory.createEmptyBorder(40, 25, 40, 25));

        JLabel tituloMarca = new JLabel(
                "FIDNESS", SwingConstants.CENTER);

        tituloMarca.setForeground(Estilo.NARANJA);
        tituloMarca.setFont(new Font("Arial", Font.BOLD, 34));
        
        JLabel subtitulo = new JLabel(
                "Sistema de gestión de ejercicios",
                SwingConstants.CENTER);

        subtitulo.setForeground(Estilo.NEGRO);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 17));
        
        JPanel panelTextos = new JPanel(
                new GridLayout(2, 1, 5, 5));

        panelTextos.setOpaque(false);
        panelTextos.add(tituloMarca);
        panelTextos.add(subtitulo);
        
        // Cargar logotipo
        JLabel imagenLogo = new JLabel(
                "", SwingConstants.CENTER);

        java.net.URL recurso = getClass().getResource(
                "/logofidnes.png");

        if (recurso != null) {

            ImageIcon icono = new ImageIcon(recurso);

            Image imagen = icono.getImage().getScaledInstance(
                    220, 220, Image.SCALE_SMOOTH);

            imagenLogo.setIcon(new ImageIcon(imagen));

        } else {

            imagenLogo.setText("FIDNESS");
            imagenLogo.setForeground(Estilo.NARANJA);
        }

        panelIzquierdo.add(panelTextos, BorderLayout.NORTH);
        panelIzquierdo.add(imagenLogo, BorderLayout.CENTER);
     
        // Panel derecho
        JPanel panelDerecho = new JPanel(
                new BorderLayout(10, 20));
        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(45, 55, 45, 55));
        
        JLabel lblTitulo = new JLabel("MODIFICAR EJERCICIO", SwingConstants.CENTER);
        Estilo.configurarTitulo(lblTitulo);


        // Formulario
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Estilo.BLANCO);
        formulario.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, 0, 7, 0);

        txtNombre = new JTextField();
        cmbTipo = new JComboBox<>(TipoEjercicio.values());

        txtDescripcion = new JTextArea(5, 40);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        txtInstrucciones = new JTextArea(10, 20);
        txtInstrucciones.setLineWrap(true);
        txtInstrucciones.setWrapStyleWord(true);

        configurarCampo(txtNombre);
        configurarArea(txtDescripcion);
        configurarArea(txtInstrucciones);

        int fila = 0;

        agregarCampo(formulario, gbc, fila++, "NOMBRE DEL EJERCICIO",
                txtNombre);

        agregarCampo(formulario, gbc, fila++, "TIPO DE EJERCICIO",
                cmbTipo);

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(500, 120));
        
        agregarCampo(formulario, gbc, fila++, "DESCRIPCIÓN",
                scrollDescripcion);

        agregarCampo(formulario, gbc, fila++, "INSTRUCCIONES",
                new JScrollPane(txtInstrucciones));

        // Botones
        JButton btnGuardar = new JButton("GUARDAR CAMBIOS");
        JButton btnCancelar = new JButton("CANCELAR");

        configurarBoton(btnGuardar, Estilo.NARANJA, Color.WHITE);
        configurarBoton(btnCancelar, Color.darkGray, Color.WHITE);

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> cerrarVentana());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Estilo.BLANCO);
        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 30, 40));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Estilo.BLANCO);
        centro.add(formulario, BorderLayout.CENTER);
        centro.add(panelBotones, BorderLayout.SOUTH);

        panelDerecho.add(centro, BorderLayout.CENTER);
        principal.add(
                barraSuperior,
                BorderLayout.NORTH);
        principal.add(panelIzquierdo, BorderLayout.WEST);
        principal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(principal);
    }

    private void agregarCampo(
            JPanel formulario,
            GridBagConstraints gbc,
            int fila,
            String titulo,
            java.awt.Component campo) {

        gbc.gridy = fila * 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
        etiqueta.setForeground(Estilo.NEGRO);
        formulario.add(etiqueta, gbc);

        gbc.gridy = fila * 2 + 1;
        formulario.add(campo, gbc);
    }

    private void configurarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(400, 36));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
    }

    private void configurarArea(JTextArea area) {
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }

    private void configurarBoton(
            JButton boton, Color fondo, Color texto) {

        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setPreferredSize(new Dimension(170, 40));
    }

    private void cargarDatosEjercicio() {

        txtNombre.setText(ejercicio.getNombre());
        txtDescripcion.setText(ejercicio.getDescripcion());
        txtInstrucciones.setText(ejercicio.getInstrucciones());
        cmbTipo.setSelectedItem(ejercicio.getTipo());
    }

    private void guardarCambios() {

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String instrucciones = txtInstrucciones.getText().trim();

        if (nombre.isEmpty()
                || descripcion.isEmpty()
                || instrucciones.isEmpty()) {

            Estilo.mostrarError(
                    "Debe completar todos los campos del ejercicio.");
            return;
        }

        try {
            administrador.modificarEjercicio(
                    repo,
                    ejercicio.getIdEjercicio(),
                    nombre,
                    descripcion,
                    instrucciones,
                    (TipoEjercicio) cmbTipo.getSelectedItem());

            Estilo.mostrarMensaje(
                    "El ejercicio se modificó correctamente.");

            cerrarVentana();

        } catch (FidnessException ex) {
            Estilo.mostrarError(ex.getMessage());
        }
    }

    private void cerrarVentana() {
        dispose();

        if (alCerrar != null) {
            alCerrar.run();
        }
    }
}
