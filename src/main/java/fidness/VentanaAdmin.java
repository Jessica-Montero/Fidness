/*
 * Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
 */
package fidness;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class VentanaAdmin extends JFrame {

    private static final String LISTA = "LISTA";
    private static final String REGISTRAR = "REGISTRAR";
    private static final String MODIFICAR = "MODIFICAR";

    private final Administrador administrador;
    private final Repositorio repo;

    private final CardLayout cards = new CardLayout();
    private final JPanel contenido = new JPanel(cards);
    private final JPanel drawer = new JPanel();

    private JList<Ejercicio> listaEjercicios;
    private JComboBox<Ejercicio> cmbEjercicios;
    private FormularioEjercicio formRegistro;
    private FormularioEjercicio formModificar;

    private Ejercicio ejercicioActual;
    private boolean actualizandoCombo;

    public VentanaAdmin(Administrador administrador, Repositorio repo) {
        this.administrador = administrador;
        this.repo = repo;

        setTitle("Fidness - Panel del administrador");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Estilo.BLANCO);

        add(crearEncabezado(), BorderLayout.NORTH);

        drawer.setLayout(new BoxLayout(drawer, BoxLayout.Y_AXIS));
        drawer.setBackground(Estilo.BLANCO);
        drawer.setBorder(new MatteBorder(0, 0, 0, 1, Estilo.GRIS));
        drawer.setPreferredSize(new Dimension(235, 0));
        drawer.setVisible(true);
        crearDrawer();
        add(drawer, BorderLayout.WEST);

        JPanel areaCentral = new JPanel(new BorderLayout());
        areaCentral.setBackground(Estilo.BLANCO);
        areaCentral.add(crearPanelMarca(), BorderLayout.WEST);

        contenido.setBackground(Estilo.BLANCO);
        contenido.add(crearPantallaLista(), LISTA);
        contenido.add(crearPantallaRegistrar(), REGISTRAR);
        contenido.add(crearPantallaModificar(), MODIFICAR);
        areaCentral.add(contenido, BorderLayout.CENTER);
        add(areaCentral, BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);

        actualizarLista();
        cards.show(contenido, LISTA);
    }

    // Panel de identidad visual fijo, compartido por todas las pantallas.
    private JPanel crearPanelMarca() {
        JPanel marca = new JPanel(new BorderLayout(5, 12));
        marca.setBackground(Estilo.BLANCO);
        marca.setPreferredSize(new Dimension(265, 0));
        marca.setBorder(new EmptyBorder(28, 18, 20, 18));

        JLabel nombre = new JLabel("FIDNESS", SwingConstants.CENTER);
        nombre.setFont(new Font("Arial", Font.BOLD, 30));
        nombre.setForeground(Estilo.NARANJA);

        JLabel subtitulo = new JLabel("Sistema de gestión de ejercicios", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(Estilo.NEGRO);

        JPanel textos = new JPanel(new GridLayout(2, 1, 2, 2));
        textos.setOpaque(false);
        textos.add(nombre);
        textos.add(subtitulo);
        marca.add(textos, BorderLayout.NORTH);

        JLabel logo = new JLabel("FIDNESS", SwingConstants.CENTER);
        java.net.URL recurso = getClass().getResource("/logofidnes.png");
        if (recurso != null) {
            ImageIcon icono = new ImageIcon(recurso);
            Image imagen = icono.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
            logo.setText("");
            logo.setIcon(new ImageIcon(imagen));
        } else {
            logo.setFont(new Font("Arial", Font.BOLD, 22));
            logo.setForeground(Estilo.NARANJA);
        }
        marca.add(logo, BorderLayout.CENTER);

        JLabel panel = new JLabel("Panel administrador", SwingConstants.CENTER);
        panel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.setForeground(new Color(145, 115, 80));
        marca.add(panel, BorderLayout.SOUTH);
        return marca;
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, Estilo.MORADO,
                        getWidth(), 0, Estilo.NARANJA));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 48));
        header.setBorder(new EmptyBorder(0, 12, 0, 12));

        JButton menu = botonIcono("\u2630");
        menu.addActionListener(e -> {
            drawer.setVisible(!drawer.isVisible());
            revalidate();
            repaint();
        });

        JLabel titulo = new JLabel("FIDNESS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Estilo.BLANCO);

        JButton usuario = botonIcono("\u25C9");
        usuario.setToolTipText("Sesión de administrador");
        header.add(menu, BorderLayout.WEST);
        header.add(titulo, BorderLayout.CENTER);
        header.add(usuario, BorderLayout.EAST);
        return header;
    }

    private JButton botonIcono(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("SansSerif", Font.PLAIN, 22));
        b.setForeground(Estilo.BLANCO);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // Sección del menú de hamburguesa
    private void crearDrawer() {
        drawer.removeAll();
        drawer.add(Box.createVerticalStrut(16));
        JLabel sesion = new JLabel("  Sesión como administrador");
        sesion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sesion.setForeground(Color.GRAY);
        sesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        drawer.add(sesion);
        drawer.add(Box.createVerticalStrut(15));
        drawer.add(itemMenu("Administrar ejercicios", LISTA));
        drawer.add(itemMenu("Registrar ejercicio", REGISTRAR));
        drawer.add(itemMenu("Modificar ejercicio", MODIFICAR));
        drawer.add(itemMenu("Cerrar sesión", null));
        drawer.add(Box.createVerticalGlue());
        JLabel pie = new JLabel("  Sistema de gestión de ejercicios");
        pie.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pie.setAlignmentX(Component.LEFT_ALIGNMENT);
        drawer.add(pie);
        drawer.add(Box.createVerticalStrut(12));
    }

    private JButton itemMenu(String texto, String destino) {
        JButton b = new JButton(texto);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBackground(Estilo.FILA);
        b.setBorder(new EmptyBorder(9, 14, 9, 10));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> {
            if (destino == null) cerrarSesion();
            else navegar(destino);
        });
        return b;
    }

    private void navegar(String destino) {
        drawer.setVisible(false);

        if (MODIFICAR.equals(destino)) {
            if (repo.getEjercicios().isEmpty()) {
                Estilo.mostrarError("No hay ejercicios para modificar.");
                revalidate();
                repaint();
                return;
            }
            prepararModificar();
        }

        cards.show(contenido, destino);
        revalidate();
        repaint();
    }

    private JPanel crearPie() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(Estilo.BLANCO);
        pie.setBorder(new EmptyBorder(4, 10, 8, 18));
        JButton salir = new JButton("⇥ Cerrar sesión");
        salir.setFont(new Font("Arial", Font.PLAIN, 10));
        salir.setForeground(Estilo.NEGRO);
        salir.setContentAreaFilled(false);
        salir.setBorderPainted(false);
        salir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salir.addActionListener(e -> cerrarSesion());
        pie.add(salir, BorderLayout.EAST);
        return pie;
    }

    // Pantalla: administrar ejercicios
    private JPanel crearPantallaLista() {
        JPanel panel = pantallaBase();
        panel.add(tituloPantalla("ADMINISTRAR EJERCICIOS"), BorderLayout.NORTH);

        listaEjercicios = new JList<>();
        configurarListaEjercicios(listaEjercicios);
        listaEjercicios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ejercicio seleccionado = listaEjercicios.getSelectedValue();
                if (seleccionado != null) ejercicioActual = seleccionado;
            }
        });
        
        configurarDobleClickModificar(listaEjercicios);

        JButton modificar = botonAccion("Modificar", Estilo.MORADO);
        JButton eliminar = botonAccion("Eliminar", Estilo.NARANJA);
        modificar.addActionListener(e -> modificarSeleccionado());
        eliminar.addActionListener(e -> eliminarSeleccionado());

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        inferior.setOpaque(false);
        inferior.add(modificar);
        inferior.add(eliminar);

        JPanel centro = new JPanel(new BorderLayout(10, 12));
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(18, 8, 8, 8));
        centro.add(etiqueta("EJERCICIOS DISPONIBLES"), BorderLayout.NORTH);
        centro.add(new JScrollPane(listaEjercicios), BorderLayout.CENTER);
        centro.add(inferior, BorderLayout.SOUTH);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    private void configurarDobleClickModificar(JList<Ejercicio> lista) {
        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() != 2
                        || !javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }

                int indice = lista.locationToIndex(e.getPoint());

                if (indice >= 0) {
                    java.awt.Rectangle fila = lista.getCellBounds(indice, indice);

                    if (fila != null && fila.contains(e.getPoint())) {
                        lista.setSelectedIndex(indice);
                        modificarSeleccionado();
                    }
                }
            }
        });
    }

    private void modificarSeleccionado() {
        Ejercicio seleccionado = listaEjercicios.getSelectedValue();
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio de la lista.");
            return;
        }
        ejercicioActual = seleccionado;
        navegar(MODIFICAR);
    }

    private void eliminarSeleccionado() {
        Ejercicio seleccionado = listaEjercicios.getSelectedValue();
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio de la lista.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el ejercicio " + seleccionado.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                administrador.eliminarEjercicio(repo, seleccionado.getIdEjercicio());

                if (seleccionado == ejercicioActual) ejercicioActual = null;

                actualizarLista();
                Estilo.mostrarMensaje("Ejercicio eliminado correctamente.");

            } catch (FidnessException e) {
                Estilo.mostrarError(e.getMessage());
            }
        }
    }

    private void actualizarLista() {
        if (listaEjercicios == null) return;
        listaEjercicios.setListData(
                repo.getEjercicios().toArray(new Ejercicio[0]));
    }

    // Pantalla: registrar ejercicio
    private JPanel crearPantallaRegistrar() {
        JPanel panel = pantallaBase();
        panel.add(tituloPantalla("REGISTRAR EJERCICIO"), BorderLayout.NORTH);

        formRegistro = new FormularioEjercicio();

        JButton registrar = botonAccion("REGISTRAR", Estilo.NARANJA);
        JButton cancelar = botonAccion("CANCELAR", Color.DARK_GRAY);
        registrar.addActionListener(e -> registrarEjercicio());
        cancelar.addActionListener(e -> {
            formRegistro.limpiar();
            navegar(LISTA);
        });

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 0));
        botones.setOpaque(false);
        botones.add(registrar);
        botones.add(cancelar);

        JPanel centro = new JPanel(new BorderLayout(10, 14));
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(18, 8, 8, 8));
        centro.add(formRegistro.panel, BorderLayout.CENTER);
        centro.add(botones, BorderLayout.SOUTH);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    private void registrarEjercicio() {
        try {
            Ejercicio ejercicio = new Ejercicio(
                    0,
                    formRegistro.txtNombre.getText().trim(),
                    formRegistro.txtDescripcion.getText().trim(),
                    formRegistro.txtInstrucciones.getText().trim(),
                    (TipoEjercicio) formRegistro.cmbTipo.getSelectedItem());

            administrador.registrarEjercicio(repo, ejercicio);

            formRegistro.limpiar();
            actualizarLista();
            Estilo.mostrarMensaje("Ejercicio registrado correctamente.");
            navegar(LISTA);

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    // Pantalla: modificar ejercicio
    private JPanel crearPantallaModificar() {
        JPanel panel = pantallaBase();
        panel.add(tituloPantalla("MODIFICAR EJERCICIO"), BorderLayout.NORTH);

        cmbEjercicios = new JComboBox<>();
        cmbEjercicios.addActionListener(e -> {
            if (!actualizandoCombo) {
                Ejercicio seleccionado = (Ejercicio) cmbEjercicios.getSelectedItem();
                if (seleccionado != null) {
                    ejercicioActual = seleccionado;
                    formModificar.cargar(seleccionado);
                }
            }
        });

        JPanel bloqueSeleccion = new JPanel(new BorderLayout(8, 8));
        bloqueSeleccion.setOpaque(false);
        bloqueSeleccion.add(etiqueta("SELECCIONE EL EJERCICIO A MODIFICAR"), BorderLayout.NORTH);
        bloqueSeleccion.add(cmbEjercicios, BorderLayout.CENTER);

        formModificar = new FormularioEjercicio();

        JButton guardar = botonAccion("GUARDAR CAMBIOS", Estilo.NARANJA);
        JButton cancelar = botonAccion("CANCELAR", Color.DARK_GRAY);
        guardar.addActionListener(e -> guardarCambios());
        cancelar.addActionListener(e -> navegar(LISTA));

        JPanel botones = new JPanel(new GridLayout(1, 2, 10, 0));
        botones.setOpaque(false);
        botones.add(guardar);
        botones.add(cancelar);

        JPanel formulario = new JPanel(new BorderLayout(10, 14));
        formulario.setOpaque(false);
        formulario.add(formModificar.panel, BorderLayout.CENTER);
        formulario.add(botones, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new BorderLayout(10, 14));
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(18, 8, 8, 8));
        centro.add(bloqueSeleccion, BorderLayout.NORTH);
        centro.add(formulario, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // Recarga el combo con los ejercicios actuales y muestra el seleccionado.
    private void prepararModificar() {
        actualizandoCombo = true;
        cmbEjercicios.removeAllItems();
        for (Ejercicio ejercicio : repo.getEjercicios()) {
            cmbEjercicios.addItem(ejercicio);
        }
        actualizandoCombo = false;

        if (repo.getEjercicios().isEmpty()) {
            ejercicioActual = null;
            formModificar.limpiar();
            return;
        }

        if (ejercicioActual == null
                || !repo.getEjercicios().contains(ejercicioActual)) {
            ejercicioActual = repo.getEjercicios().get(0);
        }

        cmbEjercicios.setSelectedItem(ejercicioActual);
        formModificar.cargar(ejercicioActual);
    }

    private void guardarCambios() {
        Ejercicio seleccionado = (Ejercicio) cmbEjercicios.getSelectedItem();
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio.");
            return;
        }

        try {
            administrador.modificarEjercicio(
                    repo,
                    seleccionado.getIdEjercicio(),
                    formModificar.txtNombre.getText().trim(),
                    formModificar.txtDescripcion.getText().trim(),
                    formModificar.txtInstrucciones.getText().trim(),
                    (TipoEjercicio) formModificar.cmbTipo.getSelectedItem());

            actualizarLista();
            Estilo.mostrarMensaje("El ejercicio se modificó correctamente.");
            navegar(LISTA);

        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    // Formulario reutilizable (registrar y modificar)
    private class FormularioEjercicio {

        final JPanel panel = new JPanel(new GridBagLayout());
        final JTextField txtNombre = new JTextField();
        final JComboBox<TipoEjercicio> cmbTipo =
                new JComboBox<>(TipoEjercicio.values());
        final JTextArea txtDescripcion = new JTextArea(3, 20);
        final JTextArea txtInstrucciones = new JTextArea(5, 20);

        FormularioEjercicio() {
            panel.setOpaque(false);

            Estilo.configurarCampo(txtNombre);
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
            txtInstrucciones.setLineWrap(true);
            txtInstrucciones.setWrapStyleWord(true);
            Estilo.configurarAreaTexto(txtDescripcion);
            Estilo.configurarAreaTexto(txtInstrucciones);

            int fila = 0;
            agregar(etiqueta("NOMBRE DEL EJERCICIO"), fila++, 0, 4);
            agregar(txtNombre, fila++, 0, 12);
            agregar(etiqueta("TIPO DE EJERCICIO"), fila++, 0, 4);
            agregar(cmbTipo, fila++, 0, 12);
            agregar(etiqueta("DESCRIPCIÓN"), fila++, 0, 4);
            agregar(new JScrollPane(txtDescripcion), fila++, 0.35, 12);
            agregar(etiqueta("INSTRUCCIONES"), fila++, 0, 4);
            agregar(new JScrollPane(txtInstrucciones), fila, 0.65, 0);
        }

        private void agregar(Component c, int fila, double pesoY, int espacioInferior) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = fila;
            gbc.weightx = 1;
            gbc.weighty = pesoY;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, espacioInferior, 0);
            panel.add(c, gbc);
        }

        void limpiar() {
            txtNombre.setText("");
            cmbTipo.setSelectedIndex(0);
            txtDescripcion.setText("");
            txtInstrucciones.setText("");
        }

        void cargar(Ejercicio ejercicio) {
            txtNombre.setText(ejercicio.getNombre());
            cmbTipo.setSelectedItem(ejercicio.getTipo());
            txtDescripcion.setText(ejercicio.getDescripcion());
            txtInstrucciones.setText(ejercicio.getInstrucciones());
            txtDescripcion.setCaretPosition(0);
            txtInstrucciones.setCaretPosition(0);
        }
    }

    private JPanel pantallaBase() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(Estilo.BLANCO);
        p.setBorder(new EmptyBorder(24, 24, 18, 28));
        return p;
    }

    private JLabel tituloPantalla(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 15));
        l.setForeground(Estilo.NEGRO);
        l.setBorder(new MatteBorder(0, 0, 1, 0, Estilo.GRIS));
        return l;
    }

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(Estilo.NEGRO);
        return l;
    }

    private JButton botonAccion(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        b.setForeground(Estilo.BLANCO);
        b.setBackground(fondo);
        b.setOpaque(true);
        b.setBorder(new EmptyBorder(9, 14, 9, 14));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void configurarListaEjercicios(JList<Ejercicio> lista) {
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(Estilo.BLANCO);
        lista.setFont(new Font("Arial", Font.PLAIN, 12));
        lista.setFixedCellHeight(76);
        lista.setBorder(new EmptyBorder(4, 4, 4, 4));
        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean selected, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, selected, focus);
                if (value instanceof Ejercicio) {
                    Ejercicio ej = (Ejercicio) value;
                    label.setText("<html><b>" + escapar(ej.getNombre()) + "</b><br>Tipo: "
                            + escapar(String.valueOf(ej.getTipo())) + "<br>"
                            + escapar(ej.getDescripcion()) + "</html>");
                    label.setBorder(BorderFactory.createCompoundBorder(
                            new MatteBorder(0, 3, 0, 0, Estilo.NEGRO),
                            new EmptyBorder(7, 10, 7, 8)));
                }
                label.setFont(new Font("Arial", Font.PLAIN, 11));
                return label;
            }
        });
    }

    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private void cerrarSesion() {
        dispose();
        VentanaLogin login = new VentanaLogin(repo);
        login.setVisible(true);
    }
}
