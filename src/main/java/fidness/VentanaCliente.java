
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
import java.io.IOException;
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

public class VentanaCliente extends JFrame {

    private Cliente cliente;
    private Repositorio repo;

    private JList<Ejercicio> listaEjercicios;
    private JList<Ejercicio> listaRutina;

    private JComboBox<String> cmbFiltro;
    private JComboBox<Rutina> cmbRutinas;

    private JTextField txtNombreRutina;
    private JTextArea txtDetalle;

    private Rutina rutinaActual;

    private boolean actualizandoRutinas = false;

    public VentanaCliente(Cliente cliente, Repositorio repo) {

        this.cliente = cliente;
        this.repo = repo;

        setTitle("Fidness - Catálogo de ejercicios");
        setSize(1150, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
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

        barraSuperior.setPreferredSize(new Dimension(1150, 18));

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

        // Panel derecho: contenido del cliente
        JPanel panelDerecho = new JPanel(
                new BorderLayout(8, 10));

        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel titulo = new JLabel(
                "Catálogo de ejercicios y mis rutinas",
                SwingConstants.CENTER);

        Estilo.configurarTitulo(titulo);

        // Filtro de categorías
        cmbFiltro = new JComboBox<>();
        cmbFiltro.addItem("Todos");

        for (TipoEjercicio tipo : TipoEjercicio.values()) {
            cmbFiltro.addItem(tipo.name());
        }

        cmbFiltro.addActionListener(e -> filtrarEjercicios());

        // Listas
        listaEjercicios = new JList<>();
        listaRutina = new JList<>();

        // Panel del catálogo
        JPanel panelCatalogo = new JPanel(
                new BorderLayout(5, 8));

        panelCatalogo.setBackground(Estilo.BLANCO);

        JLabel etiquetaFiltro = new JLabel("Filtrar por categoría:");
        etiquetaFiltro.setFont(new Font("Arial", Font.BOLD, 13));
        etiquetaFiltro.setForeground(Estilo.NEGRO);

        JPanel panelFiltro = new JPanel(
                new GridLayout(2, 1, 4, 4));

        panelFiltro.setBackground(Estilo.BLANCO);
        panelFiltro.add(etiquetaFiltro);
        panelFiltro.add(cmbFiltro);

        JLabel etiquetaCatalogo = new JLabel("Ejercicios disponibles");
        etiquetaCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaCatalogo.setForeground(Estilo.NEGRO);

        JPanel panelListaCatalogo = new JPanel(
                new BorderLayout(5, 5));

        panelListaCatalogo.setBackground(Estilo.BLANCO);
        panelListaCatalogo.add(
                etiquetaCatalogo, BorderLayout.NORTH);

        panelListaCatalogo.add(
                new JScrollPane(listaEjercicios),
                BorderLayout.CENTER);

        panelCatalogo.add(panelFiltro, BorderLayout.NORTH);
        panelCatalogo.add(panelListaCatalogo, BorderLayout.CENTER);

        // Panel de detalles
        txtDetalle = new JTextArea();
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);

        Estilo.configurarAreaTexto(txtDetalle);

        JPanel panelDetalle = new JPanel(
                new BorderLayout(5, 8));

        panelDetalle.setBackground(Estilo.BLANCO);

        JLabel etiquetaDetalle = new JLabel("Detalle del ejercicio");
        etiquetaDetalle.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaDetalle.setForeground(Estilo.NEGRO);

        panelDetalle.add(etiquetaDetalle, BorderLayout.NORTH);
        panelDetalle.add(
                new JScrollPane(txtDetalle),
                BorderLayout.CENTER);

        // Panel de rutinas
        JPanel panelRutina = new JPanel(
                new BorderLayout(5, 8));

        panelRutina.setBackground(Estilo.BLANCO);

        cmbRutinas = new JComboBox<>();

        txtNombreRutina = new JTextField();
        Estilo.configurarCampo(txtNombreRutina);

        JPanel panelNombre = new JPanel(
                new GridLayout(4, 1, 4, 4));

        panelNombre.setBackground(Estilo.BLANCO);

        JLabel etiquetaRutinas = new JLabel("Mis rutinas:");
        etiquetaRutinas.setFont(new Font("Arial", Font.BOLD, 13));
        etiquetaRutinas.setForeground(Estilo.NEGRO);

        JLabel etiquetaNombre = new JLabel("Nombre de la rutina:");
        etiquetaNombre.setFont(new Font("Arial", Font.BOLD, 13));
        etiquetaNombre.setForeground(Estilo.NEGRO);

        panelNombre.add(etiquetaRutinas);
        panelNombre.add(cmbRutinas);
        panelNombre.add(etiquetaNombre);
        panelNombre.add(txtNombreRutina);

        listaRutina = new JList<>();

        JLabel etiquetaEjerciciosRutina = new JLabel(
                "Ejercicios de la rutina");

        etiquetaEjerciciosRutina.setFont(
                new Font("Arial", Font.BOLD, 14));

        etiquetaEjerciciosRutina.setForeground(Estilo.NEGRO);

        JPanel panelListaRutina = new JPanel(
                new BorderLayout(5, 5));

        panelListaRutina.setBackground(Estilo.BLANCO);

        panelListaRutina.add(
                etiquetaEjerciciosRutina,
                BorderLayout.NORTH);

        panelListaRutina.add(
                new JScrollPane(listaRutina),
                BorderLayout.CENTER);

        panelRutina.add(panelNombre, BorderLayout.NORTH);
        panelRutina.add(panelListaRutina, BorderLayout.CENTER);

        cmbRutinas.addActionListener(e -> {

            if (!actualizandoRutinas) {

                rutinaActual = (Rutina) cmbRutinas.getSelectedItem();

                if (rutinaActual != null) {
                    txtNombreRutina.setText(
                            rutinaActual.getNombre());
                } else {
                    txtNombreRutina.setText("");
                }

                actualizarListaRutina();
            }
        });

        // Botones
        JButton btnDetalle = new JButton("Ver detalle");
        JButton btnCrearRutina = new JButton("Crear rutina");
        JButton btnAgregar = new JButton("Agregar ejercicio");
        JButton btnQuitar = new JButton("Quitar ejercicio");
        JButton btnExportar = new JButton("Exportar rutina");
        JButton btnEliminarRutina = new JButton("Eliminar rutina");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        Estilo.configurarBoton(btnDetalle);
        Estilo.configurarBoton(btnCrearRutina);
        Estilo.configurarBoton(btnAgregar);
        Estilo.configurarBoton(btnQuitar);
        Estilo.configurarBoton(btnExportar);
        Estilo.configurarBoton(btnEliminarRutina);
        Estilo.configurarBoton(btnCerrarSesion);

        btnDetalle.addActionListener(e -> mostrarDetalle());
        btnCrearRutina.addActionListener(e -> crearRutina());
        btnAgregar.addActionListener(e -> agregarEjercicio());
        btnQuitar.addActionListener(e -> quitarEjercicio());
        btnExportar.addActionListener(e -> exportarRutina());
        btnEliminarRutina.addActionListener(e -> eliminarRutina());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        JPanel panelBotones = new JPanel(
                new GridLayout(4, 2, 6, 6));

        panelBotones.setBackground(Estilo.BLANCO);

        panelBotones.add(btnDetalle);
        panelBotones.add(btnCrearRutina);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnQuitar);
        panelBotones.add(btnExportar);
        panelBotones.add(btnEliminarRutina);
        panelBotones.add(btnCerrarSesion);

        // Distribución de catálogo, detalles y rutinas
        JPanel panelContenido = new JPanel(
                new GridLayout(1, 3, 12, 0));

        panelContenido.setBackground(Estilo.BLANCO);

        panelContenido.add(panelCatalogo);
        panelContenido.add(panelDetalle);
        panelContenido.add(panelRutina);

        // Panel de contenido general
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

        filtrarEjercicios();
        actualizarComboRutinas();
    }

    private void filtrarEjercicios() {

        String seleccion = (String) cmbFiltro.getSelectedItem();

        TipoEjercicio tipo = null;

        if (seleccion != null && !seleccion.equals("Todos")) {
            tipo = TipoEjercicio.valueOf(seleccion);
        }

        ArrayList<Ejercicio> resultado =
                cliente.filtrarEjercicios(repo, tipo);

        listaEjercicios.setListData(
                resultado.toArray(new Ejercicio[0]));
    }

    private void mostrarDetalle() {

        Ejercicio seleccionado = listaEjercicios.getSelectedValue();

        if (seleccionado == null) {
            seleccionado = listaRutina.getSelectedValue();
        }

        if (seleccionado == null) {
            Estilo.mostrarError(
                    "Seleccione un ejercicio del catálogo o de su rutina.");
            return;
        }

        txtDetalle.setText(
                "Nombre: " + seleccionado.getNombre()
                + "\nCategoría: " + seleccionado.getTipo()
                + "\nDescripción: " + seleccionado.getDescripcion()
                + "\nInstrucciones: "
                + seleccionado.getInstrucciones());
    }

    private void crearRutina() {

        try {

            rutinaActual = cliente.crearRutina(
                    txtNombreRutina.getText());

            repo.guardar();

            actualizarComboRutinas();

            cmbRutinas.setSelectedItem(rutinaActual);

            actualizarListaRutina();

            Estilo.mostrarMensaje(
                    "Rutina creada correctamente.");

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void agregarEjercicio() {

        if (rutinaActual == null) {
            Estilo.mostrarError(
                    "Primero debe crear una rutina.");
            return;
        }

        Ejercicio seleccionado =
                listaEjercicios.getSelectedValue();

        if (seleccionado == null) {
            Estilo.mostrarError(
                    "Seleccione un ejercicio.");
            return;
        }

        try {

            rutinaActual.agregarEjercicio(seleccionado);

            repo.guardar();

            actualizarListaRutina();

            Estilo.mostrarMensaje(
                    "Ejercicio agregado a la rutina.");

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void quitarEjercicio() {

        if (rutinaActual == null) {
            Estilo.mostrarError(
                    "No hay una rutina seleccionada.");
            return;
        }

        Ejercicio seleccionado =
                listaRutina.getSelectedValue();

        if (seleccionado == null) {
            Estilo.mostrarError(
                    "Seleccione un ejercicio de la rutina.");
            return;
        }

        rutinaActual.eliminarEjercicio(
                seleccionado.getIdEjercicio());

        repo.guardar();

        actualizarListaRutina();
    }

    private void actualizarListaRutina() {

        if (rutinaActual != null) {

            listaRutina.setListData(
                    rutinaActual.getEjercicios().toArray(
                            new Ejercicio[0]));

        } else {

            listaRutina.setListData(new Ejercicio[0]);
        }
    }

    private void actualizarComboRutinas() {

        actualizandoRutinas = true;

        cmbRutinas.removeAllItems();

        for (Rutina rutina : cliente.getRutinas()) {
            cmbRutinas.addItem(rutina);
        }

        if (rutinaActual != null) {

            cmbRutinas.setSelectedItem(rutinaActual);

        } else if (cmbRutinas.getItemCount() > 0) {

            cmbRutinas.setSelectedIndex(
                    cmbRutinas.getItemCount() - 1);

            rutinaActual =
                    (Rutina) cmbRutinas.getSelectedItem();
        }

        actualizandoRutinas = false;

        if (rutinaActual != null) {
            txtNombreRutina.setText(
                    rutinaActual.getNombre());
        } else {
            txtNombreRutina.setText("");
        }

        actualizarListaRutina();
    }

    private void eliminarRutina() {

        if (rutinaActual == null) {
            Estilo.mostrarError("Seleccione una rutina.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la rutina "
                + rutinaActual.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            cliente.eliminarRutina(rutinaActual);

            rutinaActual = null;

            repo.guardar();

            txtNombreRutina.setText("");

            actualizarComboRutinas();

            Estilo.mostrarMensaje(
                    "Rutina eliminada correctamente.");
        }
    }

    private void exportarRutina() {

        if (rutinaActual == null) {
            Estilo.mostrarError(
                    "Primero debe crear una rutina.");
            return;
        }

        try {

            rutinaActual.exportarRutina(
                    rutinaActual.getNombre() + ".txt");

            Estilo.mostrarMensaje(
                    "Rutina exportada correctamente.");

        } catch (IOException e) {

            Estilo.mostrarError(
                    "No se pudo exportar la rutina: "
                    + e.getMessage());
        }
    }

    private void cerrarSesion() {

        dispose();

        VentanaLogin login = new VentanaLogin(repo);
        login.setVisible(true);
    }
}
