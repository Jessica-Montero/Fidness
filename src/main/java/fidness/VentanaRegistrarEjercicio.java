
/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
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

public class VentanaRegistrarEjercicio extends JFrame {

    private Administrador administrador;
    private Repositorio repo;
    private Runnable alCerrar;

    private JTextField txtNombre;
    private JComboBox<TipoEjercicio> cmbTipo;
    private JTextArea txtDescripcion;
    private JTextArea txtInstrucciones;

    public VentanaRegistrarEjercicio(
            Administrador administrador,
            Repositorio repo,
            Runnable alCerrar) {

        this.administrador = administrador;
        this.repo = repo;
        this.alCerrar = alCerrar;

        setTitle("Fidness - Registrar ejercicio");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilo.BLANCO);

        // Barra superior con degradado
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

        // Panel izquierdo: marca Fidness
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

        // Panel derecho: formulario de registro
        JPanel panelDerecho = new JPanel(
                new BorderLayout(15, 20));

        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(45, 55, 45, 55));

        JLabel titulo = new JLabel(
                "REGISTRAR EJERCICIO",
                SwingConstants.CENTER);

        Estilo.configurarTitulo(titulo);

        // Campos del formulario
        JPanel panelFormulario = new JPanel(
                new GridLayout(8, 1, 7, 5));

        panelFormulario.setBackground(Estilo.BLANCO);

        JLabel etiquetaNombre = new JLabel(
                "NOMBRE DEL EJERCICIO");

        txtNombre = new JTextField();
        txtNombre.setToolTipText("Escriba el nombre");

        JLabel etiquetaTipo = new JLabel(
                "TIPO DE EJERCICIO");

        cmbTipo = new JComboBox<>(TipoEjercicio.values());

        JLabel etiquetaDescripcion = new JLabel(
                "DESCRIPCIÓN");

        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        Estilo.configurarAreaTexto(txtDescripcion);

        JLabel etiquetaInstrucciones = new JLabel(
                "INSTRUCCIONES");

        txtInstrucciones = new JTextArea(3, 20);
        txtInstrucciones.setLineWrap(true);
        txtInstrucciones.setWrapStyleWord(true);
        Estilo.configurarAreaTexto(txtInstrucciones);

        Estilo.configurarCampo(txtNombre);

        Font fuenteEtiqueta = new Font(
                "Arial", Font.BOLD, 13);

        etiquetaNombre.setFont(fuenteEtiqueta);
        etiquetaTipo.setFont(fuenteEtiqueta);
        etiquetaDescripcion.setFont(fuenteEtiqueta);
        etiquetaInstrucciones.setFont(fuenteEtiqueta);

        panelFormulario.add(etiquetaNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(etiquetaTipo);
        panelFormulario.add(cmbTipo);
        panelFormulario.add(etiquetaDescripcion);
        panelFormulario.add(new JScrollPane(txtDescripcion));
        panelFormulario.add(etiquetaInstrucciones);
        panelFormulario.add(new JScrollPane(txtInstrucciones));

        // Botón registrar
        JButton btnRegistrar = new JButton("REGISTRAR");
        Estilo.configurarBoton(btnRegistrar);

        btnRegistrar.setPreferredSize(
                new Dimension(150, 40));

        btnRegistrar.addActionListener(
                e -> registrarEjercicio());

        // Botón cancelar
        JButton btnCancelar = new JButton("CANCELAR");
        Estilo.configurarBotonGris(btnCancelar);

        btnCancelar.setPreferredSize(
                new Dimension(150, 40));

        btnCancelar.addActionListener(
                e -> cerrarVentana());

        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 0));

        panelBotones.setBackground(Estilo.BLANCO);
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        panelDerecho.add(titulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        // Distribución general
        JPanel contenido = new JPanel(
                new GridLayout(1, 2));

        contenido.setBackground(Estilo.BLANCO);
        contenido.add(panelIzquierdo);
        contenido.add(panelDerecho);

        panelPrincipal.add(barraSuperior, BorderLayout.NORTH);
        panelPrincipal.add(contenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void registrarEjercicio() {

        try {

            Ejercicio ejercicio = new Ejercicio(
                    0,
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    txtInstrucciones.getText(),
                    (TipoEjercicio) cmbTipo.getSelectedItem());

            administrador.registrarEjercicio(repo, ejercicio);

            Estilo.mostrarMensaje(
                    "Ejercicio registrado correctamente.");

            cerrarVentana();

        } catch (FidnessException e) {

            Estilo.mostrarError(e.getMessage());
        }
    }

    private void cerrarVentana() {

        dispose();

        if (alCerrar != null) {
            alCerrar.run();
        }
    }
}
