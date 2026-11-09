/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Repositorio implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Ejercicio> ejercicios;
    private ArrayList<Usuario> usuarios;

    private static final String ARCHIVO = "fidness.dat";

    public Repositorio() {
        cargar();
    }

    @SuppressWarnings("unchecked")
    private void cargar() {

        File archivo = new File(ARCHIVO);

        if (archivo.exists()) {

            try (ObjectInputStream entrada =
                    new ObjectInputStream(
                            new FileInputStream(archivo))) {

                Repositorio datos = (Repositorio) entrada.readObject();

                this.ejercicios = datos.ejercicios;
                this.usuarios = datos.usuarios;

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar los datos: "
                        + e.getMessage());
                crearDatosIniciales();
            }

        } else {
            crearDatosIniciales();
        }
    }

    private void crearDatosIniciales() {

        ejercicios = new ArrayList<>();
        usuarios = new ArrayList<>();

        usuarios.add(new Administrador(
                "1", "admin", "admin123"));

        usuarios.add(new Cliente(
                "2", "cliente", "cliente123"));

        ejercicios.add(new Ejercicio(
                1, "Sentadillas",
                "Ejercicio para fortalecer las piernas.",
                "Colocarse de pie, flexionar las rodillas y regresar.",
                TipoEjercicio.PIERNA));

        ejercicios.add(new Ejercicio(
                2, "Curl de bíceps",
                "Ejercicio para fortalecer los brazos.",
                "Flexionar los brazos sosteniendo las pesas.",
                TipoEjercicio.BRAZO));

        guardar();
    }

    public void guardar() {

        try (ObjectOutputStream salida =
                new ObjectOutputStream(
                        new FileOutputStream(ARCHIVO))) {

            salida.writeObject(this);

        } catch (IOException e) {
            System.out.println("Error al guardar los datos: "
                    + e.getMessage());
        }
    }

    public Usuario autenticar(String nombreUsuario, String contraseña) {

        for (Usuario usuario : usuarios) {

            if (usuario.validarCredenciales(
                    nombreUsuario, contraseña)) {
                return usuario;
            }
        }

        return null;
    }

    public void agregarEjercicio(Ejercicio ejercicio)
        throws FidnessException {

    for (Ejercicio e : ejercicios) {
        if (e.getNombre().trim().equalsIgnoreCase(
                ejercicio.getNombre().trim())) {

            throw new FidnessException(
                    "Ya existe un ejercicio con ese nombre.");
        }
    }

    int nuevoId = 1;

    for (Ejercicio e : ejercicios) {
        if (e.getIdEjercicio() >= nuevoId) {
            nuevoId = e.getIdEjercicio() + 1;
        }
    }

    ejercicio.setIdEjercicio(nuevoId);
    ejercicios.add(ejercicio);
    guardar();
}

    public Ejercicio buscarEjercicio(int id) {

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getIdEjercicio() == id) {
                return ejercicio;
            }
        }

        return null;
    }

    public boolean eliminarEjercicio(int id) {

        Ejercicio ejercicio = buscarEjercicio(id);

        if (ejercicio != null) {
            ejercicios.remove(ejercicio);
            return true;
        }

        return false;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}