/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import javax.swing.JFrame;

public class Administrador extends Usuario {

    public Administrador(String id, String nombreUsuario,
            String contraseña) {

        super(id, nombreUsuario, contraseña);
    }

    public void registrarEjercicio(Repositorio repo, Ejercicio ejercicio)
            throws FidnessException {

        validarEjercicio(ejercicio);
        repo.agregarEjercicio(ejercicio);
    }

    public void modificarEjercicio(Repositorio repo, int id,
            String nombre, String descripcion,
            String instrucciones, TipoEjercicio tipo)
            throws FidnessException {

        validarDatos(nombre, descripcion, instrucciones, tipo);

        Ejercicio ejercicio = repo.buscarEjercicio(id);

        if (ejercicio == null) {
            throw new FidnessException(
                    "No se encontró el ejercicio seleccionado.");
        }

        ejercicio.actualizarInformacion(
                nombre, descripcion, instrucciones, tipo);

        repo.guardar();
    }

    public void eliminarEjercicio(Repositorio repo, int id)
            throws FidnessException {

        if (!repo.eliminarEjercicio(id)) {
            throw new FidnessException(
                    "No se encontró el ejercicio que desea eliminar.");
        }

        repo.guardar();
    }

    public Ejercicio consultarEjercicio(Repositorio repo, int id) {
        return repo.buscarEjercicio(id);
    }

    private void validarEjercicio(Ejercicio ejercicio)
            throws FidnessException {

        if (ejercicio == null) {
            throw new FidnessException("El ejercicio no es válido.");
        }

        validarDatos(
                ejercicio.getNombre(),
                ejercicio.getDescripcion(),
                ejercicio.getInstrucciones(),
                ejercicio.getTipo());
    }

    private void validarDatos(String nombre, String descripcion,
            String instrucciones, TipoEjercicio tipo)
            throws FidnessException {

        if (nombre == null || nombre.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()
                || instrucciones == null || instrucciones.trim().isEmpty()
                || tipo == null) {

            throw new FidnessException(
                    "Todos los campos del ejercicio son obligatorios.");
        }
    }

    @Override
    public JFrame abrirMenu(Repositorio repo) {
        return new VentanaAdmin(this, repo);
    }
}