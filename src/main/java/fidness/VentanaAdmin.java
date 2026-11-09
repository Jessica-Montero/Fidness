
/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class VentanaAdmin extends JFrame {

    private Administrador administrador;
    private Repositorio repo;
    private JPanel panelEjercicios;

    public VentanaAdmin(
            Administrador administrador,
            Repositorio repo) {

        this.administrador = administrador;
        this.repo = repo;

        setTitle("Fidness - Administrar ejercicios");
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
        JPanel panelIzquierdo = new JPanel(
                new BorderLayout(5, 10));

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

        // Cargar logotipo
        JLabel imagenLogo = new JLabel(
                "", SwingConstants.CENTER);

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

        // Panel derecho
        JPanel panelDerecho = new JPanel(
                new BorderLayout(10, 15));

        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titulo = new JLabel(
                "ADMINISTRAR EJERCICIOS",
                SwingConstants.CENTER);

        Estilo.configurarTitulo(titulo);

        JLabel etiquetaEjercicios = new JLabel(
                "Ejercicios disponibles");

        etiquetaEjercicios.setFont(
                new Font("Arial", Font.BOLD, 16));

        etiquetaEjercicios.setForeground(Estilo.NEGRO);

        // Panel que contiene los ejercicios
        panelEjercicios = new JPanel();

        panelEjercicios.setLayout(
                new javax.swing.BoxLayout(
                        panelEjercicios,
                        javax.swing.BoxLayout.Y_AXIS));

        panelEjercicios.setBackground(Estilo.BLANCO);

        actualizarLista();

        JScrollPane scrollEjercicios = new JScrollPane(
                panelEjercicios);

        scrollEjercicios.setBorder(
                BorderFactory.createEmptyBorder());

        // Sección de ejercicios disponibles
        JPanel panelLista = new JPanel(
                new BorderLayout(5, 10));

        panelLista.setBackground(Estilo.BLANCO);
        panelLista.add(
                etiquetaEjercicios,
                BorderLayout.NORTH);

        panelLista.add(
                scrollEjercicios,
                BorderLayout.CENTER);

        // Botón registrar ejercicio
        JButton btnRegistrar = new JButton(
                "REGISTRAR EJERCICIO");

        Estilo.configurarBoton(btnRegistrar);

        btnRegistrar.setPreferredSize(
                new Dimension(230, 45));

        btnRegistrar.addActionListener(e -> abrirRegistro());

        JPanel panelRegistro = new JPanel(
                new FlowLayout(FlowLayout.CENTER));

        panelRegistro.setBackground(Estilo.BLANCO);
        panelRegistro.add(btnRegistrar);

        // Botón cerrar sesión
        JButton btnCerrarSesion = new JButton(
                "↪ Cerrar sesión");

        Estilo.configurarBotonGris(btnCerrarSesion);

        btnCerrarSesion.addActionListener(
                e -> cerrarSesion());

        JPanel panelCerrarSesion = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        panelCerrarSesion.setBackground(Estilo.BLANCO);
        panelCerrarSesion.add(btnCerrarSesion);

        // Parte inferior
        JPanel panelInferior = new JPanel(
                new BorderLayout());

        panelInferior.setBackground(Estilo.BLANCO);
        panelInferior.add(
                panelRegistro,
                BorderLayout.CENTER);

        panelInferior.add(
                panelCerrarSesion,
                BorderLayout.SOUTH);

        panelDerecho.add(titulo, BorderLayout.NORTH);
        panelDerecho.add(panelLista, BorderLayout.CENTER);
        panelDerecho.add(panelInferior, BorderLayout.SOUTH);

        // Distribución general
        JPanel contenido = new JPanel(
                new GridLayout(1, 2));

        contenido.setBackground(Estilo.BLANCO);
        contenido.add(panelIzquierdo);
        contenido.add(panelDerecho);

        panelPrincipal.add(
                barraSuperior,
                BorderLayout.NORTH);

        panelPrincipal.add(
                contenido,
                BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void actualizarLista() {

        panelEjercicios.removeAll();

        ArrayList<Ejercicio> ejercicios = repo.getEjercicios();

        for (Ejercicio ejercicio : ejercicios) {

            // Información del ejercicio
            JPanel panelFila = new JPanel(
                    new BorderLayout(10, 5));

            panelFila.setBackground(Estilo.BLANCO);
            panelFila.setBorder(
                    BorderFactory.createEmptyBorder(12, 8, 12, 8));

            JLabel informacion = new JLabel(
                    "<html><b>"
                    + ejercicio.getNombre()
                    + "</b><br>"
                    + ejercicio.getTipo()
                    + "</html>");

            informacion.setFont(
                    new Font("Arial", Font.PLAIN, 14));

            // Botones de cada ejercicio
            JButton btnModificar = new JButton("Modificar");
            JButton btnEliminar = new JButton("Eliminar");

            Estilo.configurarBotonGris(btnModificar);
            Estilo.configurarBotonGris(btnEliminar);

            btnModificar.addActionListener(
                    e -> modificarEjercicio(ejercicio));

            btnEliminar.addActionListener(
                    e -> eliminarEjercicio(ejercicio));

            JPanel panelBotones = new JPanel(
                    new FlowLayout(FlowLayout.RIGHT, 8, 0));

            panelBotones.setBackground(Estilo.BLANCO);
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);

            panelFila.add(
                    informacion,
                    BorderLayout.CENTER);

            panelFila.add(
                    panelBotones,
                    BorderLayout.EAST);

            panelEjercicios.add(panelFila);

            // Línea divisora
            JPanel linea = new JPanel();

            linea.setBackground(Estilo.GRIS);
            linea.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 1));

            panelEjercicios.add(linea);
        }

        panelEjercicios.revalidate();
        panelEjercicios.repaint();
    }

    private void abrirRegistro() {

        VentanaRegistrarEjercicio ventana =
                new VentanaRegistrarEjercicio(
                        administrador,
                        repo,
                        () -> actualizarLista());

        ventana.setVisible(true);
    }

    private void modificarEjercicio(Ejercicio seleccionado) {

    VentanaModificarEjercicio ventana =
            new VentanaModificarEjercicio(
                    administrador,
                    repo,
                    seleccionado,
                    () -> actualizarLista());

    ventana.setVisible(true);
}

    private void eliminarEjercicio(Ejercicio seleccionado) {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este ejercicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            try {

                administrador.eliminarEjercicio(
                        repo,
                        seleccionado.getIdEjercicio());

                actualizarLista();

                Estilo.mostrarMensaje(
                        "Ejercicio eliminado correctamente.");

            } catch (FidnessException e) {

                Estilo.mostrarError(e.getMessage());
            }
        }
    }

    private void cerrarSesion() {

        dispose();

        VentanaLogin login = new VentanaLogin(repo);
        login.setVisible(true);
    }
}
