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
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
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

public class VentanaCliente extends JFrame {

    private static final String CATALOGO = "CATALOGO";
    private static final String DETALLE = "DETALLE";
    private static final String RUTINAS = "RUTINAS";

    private final Cliente cliente;
    private final Repositorio repo;

    private final CardLayout cards = new CardLayout();
    private final JPanel contenido = new JPanel(cards);
    private final JPanel drawer = new JPanel();

    private JList<Ejercicio> listaEjercicios;
    private JList<Ejercicio> listaDisponiblesRutina;
    private JList<Ejercicio> listaRutina;
    private JComboBox<String> cmbFiltro;
    private JComboBox<Rutina> cmbRutinas;
    private JTextField txtNombreRutina;
    private JEditorPane txtDetalle;
    private JLabel lblNombreDetalle;

    private Rutina rutinaActual;
    private Ejercicio ejercicioActual;
    private boolean actualizandoRutinas;
    private String pantallaAnterior = CATALOGO;

    public VentanaCliente(Cliente cliente, Repositorio repo) {
        this.cliente = cliente;
        this.repo = repo;

        setTitle("Fidness - Panel del cliente");
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
        contenido.add(crearPantallaCatalogo(), CATALOGO);
        contenido.add(crearPantallaDetalle(), DETALLE);
        contenido.add(crearPantallaRutinas(), RUTINAS);
        areaCentral.add(contenido, BorderLayout.CENTER);
        add(areaCentral, BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);

        filtrarEjercicios();
        actualizarComboRutinas();
        cards.show(contenido, CATALOGO);
    }

    // Panel de identidad visual, compartido por las pantallas.
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

        JLabel panel = new JLabel("Panel cliente", SwingConstants.CENTER);
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
        usuario.setToolTipText("Sesión de cliente");
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

    private void crearDrawer() {
        drawer.removeAll();
        drawer.add(Box.createVerticalStrut(16));
        JLabel sesion = new JLabel("  Sesión como cliente");
        sesion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sesion.setForeground(Color.GRAY);
        sesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        drawer.add(sesion);
        drawer.add(Box.createVerticalStrut(15));
        drawer.add(itemMenu("Consultar catálogo de ejercicios", CATALOGO));
        drawer.add(itemMenu("Crear y exportar rutina", RUTINAS));
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
        if (RUTINAS.equals(destino)) actualizarComboRutinas();
        cards.show(contenido, destino);
        drawer.setVisible(true);
        revalidate();
        repaint();
    }

    private JPanel crearPantallaCatalogo() {
        JPanel panel = pantallaBase();
        JLabel titulo = tituloPantalla("CATÁLOGO DE EJERCICIOS");

        cmbFiltro = new JComboBox<>();
        cmbFiltro.addItem("Todos");
        for (TipoEjercicio tipo : TipoEjercicio.values()) cmbFiltro.addItem(tipo.name());
        cmbFiltro.addActionListener(e -> filtrarEjercicios());

        JLabel filtro = etiqueta("FILTRAR POR TIPO DE EJERCICIO");
        JPanel bloqueFiltro = new JPanel(new BorderLayout(8, 8));
        bloqueFiltro.setOpaque(false);
        bloqueFiltro.add(filtro, BorderLayout.NORTH);
        bloqueFiltro.add(cmbFiltro, BorderLayout.CENTER);

        listaEjercicios = new JList<>();
        configurarListaEjercicios(listaEjercicios);
        listaEjercicios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ejercicio seleccionado = listaEjercicios.getSelectedValue();
                if (seleccionado != null) ejercicioActual = seleccionado;
            }
        });
        
        configurarDobleClickDetalle(listaEjercicios, CATALOGO);

        JButton ver = botonAccion("Ver instrucciones", Estilo.MORADO);
        ver.addActionListener(e ->
                mostrarDetalle(listaEjercicios.getSelectedValue(), CATALOGO));

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);
        inferior.add(ver, BorderLayout.EAST);

        panel.add(titulo, BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout(10, 18));
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(18, 8, 8, 8));
        centro.add(bloqueFiltro, BorderLayout.NORTH);
        centro.add(new JScrollPane(listaEjercicios), BorderLayout.CENTER);
        centro.add(inferior, BorderLayout.SOUTH);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPantallaDetalle() {
        JPanel panel = pantallaBase();
        JPanel columna = new JPanel(new BorderLayout(0, 16));
        columna.setOpaque(false);
        columna.setPreferredSize(new Dimension(520, 0));

        JLabel titulo = tituloPantalla("DETALLE DEL EJERCICIO");
        titulo.setHorizontalAlignment(SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, Estilo.GRIS),
                new EmptyBorder(0, 0, 10, 0)));

        // Nombre del ejercicio
        lblNombreDetalle = new JLabel("Seleccione un ejercicio", SwingConstants.LEFT);
        lblNombreDetalle.setFont(new Font("Arial", Font.BOLD, 11));
        lblNombreDetalle.setForeground(Estilo.NEGRO);
        lblNombreDetalle.setBorder(new EmptyBorder(8, 0, 0, 0));

        txtDetalle = new JEditorPane();
        txtDetalle.setContentType("text/html");
        txtDetalle.setEditable(false);
        txtDetalle.setBackground(Estilo.BLANCO);
        txtDetalle.setBorder(new EmptyBorder(0, 0, 0, 0));

        JScrollPane scroll = new JScrollPane(txtDetalle);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Estilo.BLANCO);

        JPanel cuerpo = new JPanel(new BorderLayout(0, 14));
        cuerpo.setOpaque(false);
        cuerpo.add(lblNombreDetalle, BorderLayout.NORTH);
        cuerpo.add(scroll, BorderLayout.CENTER);

        JLabel leyenda = new JLabel("Detalle del ejercicio");
        leyenda.setFont(new Font("Arial", Font.PLAIN, 11));
        leyenda.setForeground(Color.GRAY);

        JButton volver = new JButton("\u2190 Volver");
        volver.setFont(new Font("Arial", Font.BOLD, 11));
        volver.setForeground(Estilo.MORADO);
        volver.setContentAreaFilled(false);
        volver.setBorderPainted(false);
        volver.setFocusPainted(false);
        volver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        volver.addActionListener(e -> navegar(pantallaAnterior));

        JPanel pieDetalle = new JPanel(new BorderLayout());
        pieDetalle.setOpaque(false);
        pieDetalle.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, Estilo.GRIS),
                new EmptyBorder(10, 0, 0, 0)));
        pieDetalle.add(leyenda, BorderLayout.WEST);
        pieDetalle.add(volver, BorderLayout.EAST);

        columna.add(titulo, BorderLayout.NORTH);
        columna.add(cuerpo, BorderLayout.CENTER);
        columna.add(pieDetalle, BorderLayout.SOUTH);

        panel.add(columna, BorderLayout.WEST);
        return panel;
    }

    private JPanel crearPantallaRutinas() {
        JPanel panel = pantallaBase();
        panel.add(tituloPantalla("CREAR Y EXPORTAR RUTINA"), BorderLayout.NORTH);

        JPanel cuerpo = new JPanel();
        cuerpo.setOpaque(false);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBorder(new EmptyBorder(14, 8, 8, 8));

        JLabel lblRutinas = etiqueta("MIS RUTINAS");
        cmbRutinas = new JComboBox<>();
        cmbRutinas.addActionListener(e -> {
            if (!actualizandoRutinas) {
                rutinaActual = (Rutina) cmbRutinas.getSelectedItem();
                txtNombreRutina.setText(rutinaActual == null ? "" : rutinaActual.getNombre());
                actualizarListaRutina();
            }
        });

        JLabel lblNombre = etiqueta("NOMBRE DE LA RUTINA");
        txtNombreRutina = new JTextField();
        Estilo.configurarCampo(txtNombreRutina);
        txtNombreRutina.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        cuerpo.add(lblRutinas);
        cuerpo.add(Box.createVerticalStrut(5));
        cuerpo.add(cmbRutinas);
        cuerpo.add(Box.createVerticalStrut(12));
        cuerpo.add(lblNombre);
        cuerpo.add(Box.createVerticalStrut(5));
        cuerpo.add(txtNombreRutina);
        cuerpo.add(Box.createVerticalStrut(16));
        cuerpo.add(etiqueta("EJERCICIOS DISPONIBLES"));
        cuerpo.add(Box.createVerticalStrut(5));

        listaDisponiblesRutina = new JList<>();
        configurarListaEjercicios(listaDisponiblesRutina);
        listaDisponiblesRutina.setVisibleRowCount(2);

        configurarDobleClickDetalle(listaDisponiblesRutina, RUTINAS);
        cuerpo.add(new JScrollPane(listaDisponiblesRutina));

        JButton agregar = botonAccionMini("+ Agregar ejercicio", Estilo.MORADO);
        agregar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        agregar.addActionListener(e -> agregarEjercicio());
        cuerpo.add(Box.createVerticalStrut(7));
        cuerpo.add(agregar);
        cuerpo.add(Box.createVerticalStrut(14));
        cuerpo.add(etiqueta("MI RUTINA"));
        cuerpo.add(Box.createVerticalStrut(4));

        JLabel ayuda = new JLabel("Aquí aparecerán los ejercicios seleccionados.");
        ayuda.setFont(new Font("Arial", Font.PLAIN, 11));
        ayuda.setForeground(Color.GRAY);
        cuerpo.add(ayuda);
        cuerpo.add(Box.createVerticalStrut(5));

        listaRutina = new JList<>();
        configurarListaEjercicios(listaRutina);
        listaRutina.setVisibleRowCount(2);
        
        configurarDobleClickDetalle(listaRutina, RUTINAS);

        cuerpo.add(new JScrollPane(listaRutina));

        JButton quitar = botonAccionMini("Eliminar ejercicio", Estilo.NARANJA);
        quitar.setAlignmentX(Component.LEFT_ALIGNMENT);
        quitar.addActionListener(e -> quitarEjercicio());
        cuerpo.add(Box.createVerticalStrut(7));
        cuerpo.add(quitar);
        cuerpo.add(Box.createVerticalStrut(16));

        JButton crear = botonAccion("CREAR RUTINA", Estilo.NARANJA);
        JButton exportar = botonAccion("EXPORTAR RUTINA", Color.DARK_GRAY);
        JButton eliminar = botonAccion("ELIMINAR RUTINA", Color.GRAY);
        crear.addActionListener(e -> crearRutina());
        exportar.addActionListener(e -> exportarRutina());
        eliminar.addActionListener(e -> eliminarRutina());

        JPanel botones = new JPanel(new GridLayout(1, 3, 10, 0));
        botones.setOpaque(false);
        botones.add(crear);
        botones.add(exportar);
        botones.add(eliminar);
        cuerpo.add(botones);

        panel.add(new JScrollPane(cuerpo), BorderLayout.CENTER);
        return panel;
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
    
    private JButton botonAccionMini(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.BOLD, 10));
        b.setForeground(Estilo.BLANCO);
        b.setBackground(fondo);
        b.setOpaque(true);
        b.setBorder(new EmptyBorder(8, 13, 8, 13));
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
                            + escapar(ej.getDescripcion()) + "<br><u>Ver instrucciones</u></html>");
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

    private void filtrarEjercicios() {
        if (cmbFiltro == null || listaEjercicios == null) return;
        String seleccion = (String) cmbFiltro.getSelectedItem();
        TipoEjercicio tipo = null;
        if (seleccion != null && !"Todos".equals(seleccion)) {
            tipo = TipoEjercicio.valueOf(seleccion);
        }
        ArrayList<Ejercicio> resultado = cliente.filtrarEjercicios(repo, tipo);
        listaEjercicios.setListData(resultado.toArray(new Ejercicio[0]));
        if (listaDisponiblesRutina != null) {
            listaDisponiblesRutina.setListData(resultado.toArray(new Ejercicio[0]));
        }
    }

    private void configurarDobleClickDetalle(
            JList<Ejercicio> lista, String pantallaOrigen) {

        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() != 2 ||
                        !javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }

                int indice = lista.locationToIndex(e.getPoint());

                if (indice >= 0) {
                    java.awt.Rectangle fila = lista.getCellBounds(indice, indice);

                    if (fila != null && fila.contains(e.getPoint())) {
                        lista.setSelectedIndex(indice);
                        Ejercicio seleccionado =
                                lista.getModel().getElementAt(indice);

                        mostrarDetalle(seleccionado, pantallaOrigen);
                    }
                }
            }
        });
    }

    private void mostrarDetalle(Ejercicio seleccionado, String origen) {
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio.");
            return;
        }

        ejercicioActual = seleccionado;
        pantallaAnterior = origen;

        lblNombreDetalle.setText(seleccionado.getNombre());
        txtDetalle.setText(construirHtmlDetalle(seleccionado));
        txtDetalle.setCaretPosition(0);

        navegar(DETALLE);
    }

    private String construirHtmlDetalle(Ejercicio ejercicio) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family:Arial; font-size:11pt; margin:0'>");

        html.append("<b>Tipo de ejercicio</b><br>")
            .append(escapar(String.valueOf(ejercicio.getTipo())))
            .append("<br><br>");

        html.append("<b>Descripción</b><br>")
            .append(escapar(ejercicio.getDescripcion()).replace("\n", "<br>"))
            .append("<br><br>");

        html.append("<b>Instrucciones</b>");
        html.append("<ol style='margin-top:2px; margin-bottom:0px'>");
        for (String paso : dividirInstrucciones(ejercicio.getInstrucciones())) {
            html.append("<li>").append(escapar(paso)).append("</li>");
        }
        html.append("</ol>");

        html.append("</body></html>");
        return html.toString();
    }

    private ArrayList<String> dividirInstrucciones(String texto) {
        ArrayList<String> pasos = new ArrayList<>();
        if (texto == null) return pasos;

        for (String linea : texto.split("\\R")) {
            String limpia = linea.trim().replaceFirst("^\\d+\\s*[.)\\-]\\s*", "");
            if (!limpia.isEmpty()) pasos.add(limpia);
        }

        if (pasos.size() == 1) {
            String[] frases = pasos.get(0).split("(?<=[.!?])\\s+");
            if (frases.length > 1) {
                pasos.clear();
                for (String frase : frases) {
                    if (!frase.trim().isEmpty()) pasos.add(frase.trim());
                }
            }
        }
        return pasos;
    }

    private void crearRutina() {
        try {
            String nombre = txtNombreRutina.getText().trim();
            if (nombre.isEmpty()) {
                Estilo.mostrarError("Ingrese el nombre de la rutina.");
                txtNombreRutina.requestFocus();
                return;
            }
            rutinaActual = cliente.crearRutina(nombre);
            repo.guardar();
            actualizarComboRutinas();
            cmbRutinas.setSelectedItem(rutinaActual);
            actualizarListaRutina();
            Estilo.mostrarMensaje("Rutina creada correctamente.");
        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void agregarEjercicio() {
        if (rutinaActual == null) {
            Estilo.mostrarError("Primero debe crear o seleccionar una rutina.");
            return;
        }
        Ejercicio seleccionado = listaDisponiblesRutina.getSelectedValue();
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio disponible.");
            return;
        }
        try {
            rutinaActual.agregarEjercicio(seleccionado);
            repo.guardar();
            actualizarListaRutina();
            Estilo.mostrarMensaje("Ejercicio agregado a la rutina.");
        } catch (FidnessException e) {
            Estilo.mostrarError(e.getMessage());
        }
    }

    private void quitarEjercicio() {
        if (rutinaActual == null) {
            Estilo.mostrarError("No hay una rutina seleccionada.");
            return;
        }
        Ejercicio seleccionado = listaRutina.getSelectedValue();
        if (seleccionado == null) {
            Estilo.mostrarError("Seleccione un ejercicio de su rutina.");
            return;
        }
        rutinaActual.eliminarEjercicio(seleccionado.getIdEjercicio());
        repo.guardar();
        actualizarListaRutina();
        Estilo.mostrarMensaje("Ejercicio eliminado de la rutina.");
    }

    private void actualizarListaRutina() {
        if (listaRutina == null) return;
        if (rutinaActual != null) {
            listaRutina.setListData(rutinaActual.getEjercicios().toArray(new Ejercicio[0]));
        } else {
            listaRutina.setListData(new Ejercicio[0]);
        }
    }

    private void actualizarComboRutinas() {
        if (cmbRutinas == null) return;
        actualizandoRutinas = true;
        cmbRutinas.removeAllItems();
        for (Rutina rutina : cliente.getRutinas()) cmbRutinas.addItem(rutina);

        if (rutinaActual != null && cliente.getRutinas().contains(rutinaActual)) {
            cmbRutinas.setSelectedItem(rutinaActual);
        } else if (cmbRutinas.getItemCount() > 0) {
            cmbRutinas.setSelectedIndex(cmbRutinas.getItemCount() - 1);
            rutinaActual = (Rutina) cmbRutinas.getSelectedItem();
        } else {
            rutinaActual = null;
            cmbRutinas.setSelectedIndex(-1);
        }
        actualizandoRutinas = false;
        txtNombreRutina.setText(rutinaActual == null ? "" : rutinaActual.getNombre());
        actualizarListaRutina();
    }

    private void eliminarRutina() {
        if (rutinaActual == null) {
            Estilo.mostrarError("Seleccione una rutina.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la rutina " + rutinaActual.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            cliente.eliminarRutina(rutinaActual);
            rutinaActual = null;
            repo.guardar();
            actualizarComboRutinas();
            Estilo.mostrarMensaje("Rutina eliminada correctamente.");
        }
    }

    private void exportarRutina() {
        if (rutinaActual == null) {
            Estilo.mostrarError("Primero debe crear o seleccionar una rutina.");
            return;
        }
        if (rutinaActual.getEjercicios().isEmpty()) {
            Estilo.mostrarError("No se puede exportar una rutina sin ejercicios.");
            return;
        }
        try {
            rutinaActual.exportarRutina(rutinaActual.getNombre() + ".txt");
            Estilo.mostrarMensaje("Rutina exportada correctamente.");
        } catch (IOException e) {
            Estilo.mostrarError("No se pudo exportar la rutina: " + e.getMessage());
        }
    }

    private void cerrarSesion() {
        dispose();
        VentanaLogin login = new VentanaLogin(repo);
        login.setVisible(true);
    }
}
