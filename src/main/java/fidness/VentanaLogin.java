
/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

    private Repositorio repo;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnIngresar;
    private JButton btnCancelar;

    public VentanaLogin(Repositorio repo) {

        this.repo = repo;

        setTitle("Fidness - Inicio de sesión");
        setSize(950, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
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

        // Sección izquierda
        JPanel panelIzquierdo = new JPanel(
                new BorderLayout(10, 20));

        panelIzquierdo.setBackground(Estilo.BLANCO);
        panelIzquierdo.setBorder(
                BorderFactory.createEmptyBorder(40, 25, 40, 25));

        JLabel titulo = new JLabel(
                "FIDNESS", SwingConstants.CENTER);

        titulo.setForeground(Estilo.NARANJA);
        titulo.setFont(new Font("Arial", Font.BOLD, 34));

        JLabel subtitulo = new JLabel(
                "Sistema de gestión de ejercicios",
                SwingConstants.CENTER);

        subtitulo.setForeground(Estilo.NEGRO);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 17));

        JPanel panelTextos = new JPanel(
                new GridLayout(2, 1, 5, 5));

        panelTextos.setBackground(Estilo.BLANCO);
        panelTextos.setOpaque(false);
        panelTextos.add(titulo);
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
            imagenLogo.setForeground(Estilo.BLANCO);
        }

        panelIzquierdo.add(panelTextos, BorderLayout.NORTH);
        panelIzquierdo.add(imagenLogo, BorderLayout.CENTER);

        // Sección derecha
        JPanel panelDerecho = new JPanel(
                new BorderLayout(15, 20));

        panelDerecho.setBackground(Estilo.BLANCO);
        panelDerecho.setBorder(
                BorderFactory.createEmptyBorder(70, 65, 70, 65));

        JLabel tituloLogin = new JLabel(
                "INICIAR SESIÓN", SwingConstants.CENTER);

        tituloLogin.setForeground(Estilo.MORADO);
        tituloLogin.setFont(new Font("Arial", Font.BOLD, 25));

        // Campos de usuario y contraseña
        JPanel panelCampos = new JPanel(
                new GridLayout(4, 1, 8, 8));

        panelCampos.setBackground(Estilo.BLANCO);

        JLabel etiquetaUsuario = new JLabel("Usuario:");
        etiquetaUsuario.setFont(
                new Font("Arial", Font.BOLD, 14));

        txtUsuario = new JTextField();
        Estilo.configurarCampo(txtUsuario);

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        etiquetaContraseña.setFont(
                new Font("Arial", Font.BOLD, 14));

        txtContraseña = new JPasswordField();
        Estilo.configurarCampo(txtContraseña);

        panelCampos.add(etiquetaUsuario);
        panelCampos.add(txtUsuario);
        panelCampos.add(etiquetaContraseña);
        panelCampos.add(txtContraseña);

        // Botón ingresar
        btnIngresar = new JButton("Iniciar sesión");
        Estilo.configurarBoton(btnIngresar);

        btnIngresar.setPreferredSize(
                new Dimension(140, 40));

        btnIngresar.addActionListener(
                e -> iniciarSesion());

        // Botón cancelar
        btnCancelar = new JButton("Cancelar");
        Estilo.configurarBotonGris(btnCancelar);

        btnCancelar.setPreferredSize(
                new Dimension(140, 40));

        btnCancelar.addActionListener(
                e -> dispose());

        // Contenedor de botones
        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 0));

        panelBotones.setBackground(Estilo.BLANCO);
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);
        
        // Credenciales de acceso para el profesor
        JPanel panelCredenciales = new JPanel(
                new GridLayout(2, 1, 3, 3));

        panelCredenciales.setBackground(Estilo.BLANCO);

        JLabel tituloCredenciales = new JLabel(
                "Credenciales de acceso para pruebas",
                SwingConstants.CENTER);

        tituloCredenciales.setFont(
                new Font("Arial", Font.BOLD, 12));

        tituloCredenciales.setForeground(Estilo.MORADO);

        JLabel textoCredenciales = new JLabel(
                "<html><center>"
                + "Administrador: admin / admin123"
                + "<br>Cliente: cliente / cliente123"
                + "</center></html>",
                SwingConstants.CENTER);

        textoCredenciales.setFont(
                new Font("Arial", Font.PLAIN, 12));

        textoCredenciales.setForeground(Color.GRAY);

        panelCredenciales.add(tituloCredenciales);
        panelCredenciales.add(textoCredenciales);

        // Contenedor inferior: botones y credenciales
        JPanel panelInferior = new JPanel(
                new BorderLayout(5, 12));

        panelInferior.setBackground(Estilo.BLANCO);
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(panelCredenciales, BorderLayout.SOUTH);

        // Agregar componentes al panel derecho
        panelDerecho.add(
                tituloLogin, BorderLayout.NORTH);

        panelDerecho.add(
                panelCampos, BorderLayout.CENTER);

        panelDerecho.add(
                panelInferior, BorderLayout.SOUTH);

        // Distribución de las dos secciones
        JPanel contenido = new JPanel(
                new GridLayout(1, 2));

        contenido.add(panelIzquierdo);
        contenido.add(panelDerecho);

        panelPrincipal.add(
                barraSuperior, BorderLayout.NORTH);

        panelPrincipal.add(
                contenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void iniciarSesion() {

        String usuario = txtUsuario.getText().trim();

        String contraseña = String.valueOf(
                txtContraseña.getPassword());

        Usuario usuarioEncontrado =
                repo.autenticar(usuario, contraseña);

        if (usuarioEncontrado == null) {

            Estilo.mostrarError(
                    "Usuario o contraseña incorrectos.");

        } else {

            dispose();

            JFrame menu = usuarioEncontrado.abrirMenu(repo);
            menu.setVisible(true);
        }
    }
}
