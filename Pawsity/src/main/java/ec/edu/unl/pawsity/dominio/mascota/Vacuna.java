package ec.edu.unl.pawsity.dominio.mascota;
import java.time.LocalDate;

public class Vacuna {
    private String nombre;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProximaDosis;

    public Vacuna(String nombre, LocalDate fechaAplicacion, LocalDate fechaProximaDosis) {
        this.nombre = nombre;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaProximaDosis = fechaProximaDosis;
    }

    public void obtenerDetalles() {
        System.out.println("  - Vacuna: " + nombre + " | Fecha de aplicación: " + fechaAplicacion + " | Próxima dosis: " + fechaProximaDosis);
    }

    // --- Getters y Setters obligatorios para que JSF pueda leer la información en la web ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDate getFechaProximaDosis() {
        return fechaProximaDosis;
    }

    public void setFechaProximaDosis(LocalDate fechaProximaDosis) {
        this.fechaProximaDosis = fechaProximaDosis;
    }

    public LocalDate getFechaProximaAplicacion() {
        return fechaProximaDosis;
    }
}
