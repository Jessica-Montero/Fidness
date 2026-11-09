/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Repositorio repo = new Repositorio();

            VentanaLogin ventana = new VentanaLogin(repo);

            ventana.setVisible(true);
        });
    }
}