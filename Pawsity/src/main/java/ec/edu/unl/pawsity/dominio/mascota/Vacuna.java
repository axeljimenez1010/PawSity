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
}
