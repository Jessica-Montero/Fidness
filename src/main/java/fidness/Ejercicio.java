/*
 *Jessica Montero Obando
 * Universidad Fidélitas. Avance 2
*/

package fidness;

import java.io.Serializable;

public class Ejercicio implements Serializable {

    private int idEjercicio;
    private String nombre;
    private String descripcion;
    private String instrucciones;
    private TipoEjercicio tipo;

    public Ejercicio(int idEjercicio, String nombre, String descripcion,
            String instrucciones, TipoEjercicio tipo) {

        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.tipo = tipo;
    }

    public void mostrarDetalle() {
        System.out.println("ID: " + idEjercicio);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Instrucciones: " + instrucciones);
        System.out.println("Tipo: " + tipo);
    }

    public void actualizarInformacion(String nombre, String descripcion,
            String instrucciones, TipoEjercicio tipo) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.tipo = tipo;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public TipoEjercicio getTipo() {
        return tipo;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    @Override
    public String toString() {
        return nombre + " - " + tipo;
    }
}