
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaAdmin extends JFrame {

    private Administrador administrador;
    private Repositorio repo;

    private JList<Ejercicio> listaEjercicios;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextArea txtInstrucciones;
    private JComboBox<TipoEjercicio> cmbTipo;

    public VentanaAdmin(Administrador administrador, Repositorio repo) {

        this.administrador = administrador;
        this.repo = repo;

        setTitle("Fidness - Administración de ejercicios");
        setSize(1050, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilo.BLANCO);

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

        barraSuperior.setPreferredSize(new Dimension(1050, 18));

        // Panel izquierdo: marca Fidness
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 10));
        panelIzquierdo.setBackground(Estilo.BLANCO);
        panelIzquierdo.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel tituloMarca = new JLabel(
                "FIDNESS", SwingConstants.CENTER);

        tituloMarca.setForeground(Estilo.NARANJA);
        tituloMarca.setFont(new Font("Arial", Font.BOLD, 34));

        JLabel subtitulo = new JLabel(
                "Sistema de gestión de ejercicios",
                SwingConstants.CENTER);

        subtitulo.setForeground(Estilo.NEGRO);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel panelTextos = new JPanel(
                new GridLayout(2, 1, 2, 2));

        panelTextos.setOpaque(false);
        panelTextos.add(tituloMarca);
        panelTextos.add(subtitulo);

        JLabel imagenLogo = new JLabel("", SwingConstants.CENTER);

        java.net.URL recurso = getClass().getResource(
                "/logofidnes.png");

        if (recurso != null) {

            ImageIcon icono = new ImageIcon(recurso);

            Image imagen = icono.getImage().getScaledInstance(
                    190, 190, Image.SCALE_SMOOTH);

            imagenLogo.setIcon(new ImageIcon(imagen));

        } else {
            imagenLogo.setText("FIDNESS");
            imagenLogo.setForeground(Estilo.NARANJA);
        }

        panelIzquierdo.add(panelTextos, BorderLayout.NORTH);
        panelIzquierdo.add(imagenLogo, BorderLayout.CENTER);

        // Panel derecho: administración
        JPanel panelDerecho = new JPanel(
                new BorderLayout(8, 10));

        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel titulo = new JLabel(
                "Administración de ejercicios",
                SwingConstants.CENTER);

        Estilo.configurarTitulo(titulo);

        // Lista de ejercicios
        listaEjercicios = new JList<>();
        actualizarLista();

        JPanel panelLista = new JPanel(new BorderLayout(5, 6));
        panelLista.setBackground(Estilo.BLANCO);

        JLabel etiquetaLista = new JLabel("Ejercicios registrados");
        etiquetaLista.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaLista.setForeground(Estilo.NEGRO);

        panelLista.add(etiquetaLista, BorderLayout.NORTH);
        panelLista.add(
                new JScrollPane(listaEjercicios),
                BorderLayout.CENTER);

        // Formulario
        JPanel panelFormulario = new JPanel(
                new GridLayout(4, 2, 8, 8));

        panelFormulario.setBackground(Estilo.BLANCO);

        txtNombre = new JTextField();
        txtDescripcion = new JTextArea(3, 15);
        txtInstrucciones = new JTextArea(3, 15);
        cmbTipo = new JComboBox<>(TipoEjercicio.values());

        Estilo.configurarCampo(txtNombre);
        Estilo.configurarAreaTexto(txtDescripcion);
        Estilo.configurarAreaTexto(txtInstrucciones);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(new JScrollPane(txtDescripcion));

        panelFormulario.add(new JLabel("Instrucciones:"));
        panelFormulario.add(new JScrollPane(txtInstrucciones));

        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbTipo);

        // Distribución de lista y formulario
        JPanel panelContenido = new JPanel(
                new GridLayout(1, 2, 12, 0));

        panelContenido.setBackground(Estilo.BLANCO);
        panelContenido.add(panelLista);
        panelContenido.add(panelFormulario);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        Estilo.configurarBoton(btnRegistrar);
        Estilo.configurarBoton(btnModificar);
        Estilo.configurarBoton(btnEliminar);
        Estilo.configurarBoton(btnLimpiar);
        Estilo.configurarBoton(btnCerrarSesion);

        btnRegistrar.addActionListener(e -> registrarEjercicio());
        btnModificar.addActionListener(e -> modificarEjercicio());
        btnEliminar.addActionListener(e -> eliminarEjercicio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        JPanel panelBotones = new JPanel(
                new GridLayout(1, 5, 6, 6));

        panelBotones.setBackground(Estilo.BLANCO);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrarSesion);

        listaEjercicios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEjercicioSeleccionado();
            }
        });

        panelDerecho.add(titulo, BorderLayout.NORTH);
        panelDerecho.add(panelContenido, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        // Distribución general
        JPanel contenido = new JPanel(new GridLayout(1, 2));
        contenido.setBackground(Estilo.BLANCO);

        contenido.add(panelIzquierdo);
        contenido.add(panelDerecho);

        panelPrincipal.add(barraSuperior, BorderLayout.NORTH);
        panelPrincipal.add(contenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void actualizarLista() {

        ArrayList<Ejercicio> ejercicios = repo.getEjercicios();

        listaEjercicios.setListData(
                ejercicios.toArray(new Ejercicio[0]));
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

            actualizarLista();
            limpiarCampos();

            Estilo.mostrarMensaje(
                    "Ejercicio registrado correctamente.");

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void modificarEjercicio() {

        Ejercicio seleccionado = listaEjercicios.getSelectedValue();

        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio.");
            return;
        }

        try {

            administrador.modificarEjercicio(
                    repo,
                    seleccionado.getIdEjercicio(),
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    txtInstrucciones.getText(),
                    (TipoEjercicio) cmbTipo.getSelectedItem());

            actualizarLista();

            Estilo.mostrarMensaje(
                    "Ejercicio modificado correctamente.");

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void eliminarEjercicio() {

        Ejercicio seleccionado = listaEjercicios.getSelectedValue();

        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este ejercicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            try {

                administrador.eliminarEjercicio(
                        repo, seleccionado.getIdEjercicio());

                actualizarLista();
                limpiarCampos();

                Estilo.mostrarMensaje("Ejercicio eliminado.");

            } catch (FidnessException e) {
                Estilo.mostrarError(e.getMessage());
            }
        }
    }

    private void cargarEjercicioSeleccionado() {

        Ejercicio seleccionado = listaEjercicios.getSelectedValue();

        if (seleccionado != null) {

            txtNombre.setText(seleccionado.getNombre());
            txtDescripcion.setText(seleccionado.getDescripcion());
            txtInstrucciones.setText(seleccionado.getInstrucciones());
            cmbTipo.setSelectedItem(seleccionado.getTipo());
        }
    }

    private void limpiarCampos() {

        txtNombre.setText("");
        txtDescripcion.setText("");
        txtInstrucciones.setText("");
        listaEjercicios.clearSelection();
        cmbTipo.setSelectedIndex(0);
    }

    private void cerrarSesion() {

        dispose();

        VentanaLogin login = new VentanaLogin(repo);
        login.setVisible(true);
    }
}
