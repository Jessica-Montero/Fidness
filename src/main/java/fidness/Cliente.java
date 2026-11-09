/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.util.ArrayList;
import javax.swing.JFrame;

public class Cliente extends Usuario {

    private ArrayList<Rutina> rutinas;

    public Cliente(String id, String nombreUsuario, String contraseña) {
        super(id, nombreUsuario, contraseña);
        this.rutinas = new ArrayList<>();
    }

    public ArrayList<Ejercicio> filtrarEjercicios(
            Repositorio repo, TipoEjercicio tipo) {

        ArrayList<Ejercicio> resultado = new ArrayList<>();

        for (Ejercicio ejercicio : repo.getEjercicios()) {

            if (tipo == null || ejercicio.getTipo() == tipo) {
                resultado.add(ejercicio);
            }
        }

        return resultado;
    }

    public Rutina crearRutina(String nombre) throws FidnessException {

    if (nombre == null || nombre.trim().isEmpty()) {
        throw new FidnessException(
                "Debe ingresar un nombre para la rutina.");
    }

    String nombreLimpio = nombre.trim();

    for (Rutina rutina : rutinas) {

        if (rutina.getNombre().equalsIgnoreCase(nombreLimpio)) {
            throw new FidnessException(
                    "Ya existe una rutina con ese nombre.");
        }
    }

    Rutina rutina = new Rutina(nombreLimpio);
    rutinas.add(rutina);

    return rutina;
}

    public void eliminarRutina(Rutina rutina) {
        rutinas.remove(rutina);
    }

    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }

    @Override
    public JFrame abrirMenu(Repositorio repo) {
        return new VentanaCliente(this, repo);
    }
}