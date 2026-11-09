/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.io.Serializable;
import javax.swing.JFrame;

public abstract class Usuario implements Serializable {

    private String id;
    private String nombreUsuario;
    private String contraseña;

    public Usuario(String id, String nombreUsuario, String contraseña) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public boolean validarCredenciales(String usuario, String clave) {
        return nombreUsuario.equals(usuario)
                && contraseña.equals(clave);
    }

    public abstract JFrame abrirMenu(Repositorio repo);

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}