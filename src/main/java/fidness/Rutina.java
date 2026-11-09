/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.io.Serializable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Rutina implements Serializable {

    private String nombre;
    private ArrayList<Ejercicio> ejercicios;

    public Rutina(String nombre) {
        this.nombre = nombre;
        this.ejercicios = new ArrayList<>();
    }

    public void agregarEjercicio(Ejercicio ejercicio)
            throws FidnessException {

        for (Ejercicio e : ejercicios) {
            if (e.getIdEjercicio() == ejercicio.getIdEjercicio()) {
                throw new FidnessException(
                        "Este ejercicio ya está en la rutina.");
            }
        }

        ejercicios.add(ejercicio);
    }

    public void eliminarEjercicio(int idEjercicio) {

        ejercicios.removeIf(e ->
                e.getIdEjercicio() == idEjercicio);
    }

    public void exportarRutina(String rutaArchivo)
            throws IOException {

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(rutaArchivo))) {

            escritor.write("RUTINA: " + nombre);
            escritor.newLine();
            escritor.newLine();

            for (Ejercicio ejercicio : ejercicios) {
                escritor.write("Ejercicio: " + ejercicio.getNombre());
                escritor.newLine();
                escritor.write("Tipo: " + ejercicio.getTipo());
                escritor.newLine();
                escritor.write("Descripción: "
                        + ejercicio.getDescripcion());
                escritor.newLine();
                escritor.write("Instrucciones: "
                        + ejercicio.getInstrucciones());
                escritor.newLine();
                escritor.newLine();
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    @Override
    public String toString() {
        return nombre;
    }
}