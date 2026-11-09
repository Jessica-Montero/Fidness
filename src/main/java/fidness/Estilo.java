
/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Estilo {

    public static final Color NEGRO = new Color(25, 25, 25);
    public static final Color NARANJA = new Color(255, 140, 0);
    public static final Color BLANCO = Color.WHITE;
    public static final Color MORADO = new Color(100, 65, 140);
    public static final Color GRIS = new Color(230, 230, 230);
    public static final Color FILA    = new Color(0xFDF7F2);

    public static void configurarBoton(JButton boton) {
        boton.setBackground(NARANJA);
        boton.setForeground(NEGRO);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
    }
    
    public static void configurarBotonGris(JButton boton) {
        boton.setBackground(GRIS);
        boton.setForeground(NEGRO);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
    }

    public static void configurarTitulo(JLabel titulo) {
        titulo.setForeground(NARANJA);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
    }

    public static void configurarPanel(JPanel panel) {
        panel.setBackground(GRIS);
    }

    public static void configurarCampo(JTextField campo) {
        campo.setBackground(BLANCO);
        campo.setForeground(NEGRO);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    public static void configurarAreaTexto(JTextArea area) {
        area.setBackground(BLANCO);
        area.setForeground(NEGRO);
        area.setFont(new Font("Arial", Font.PLAIN, 13));
    }

    public static void configurarSubtitulo(JLabel etiqueta) {
        etiqueta.setForeground(MORADO);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Fidness",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
