package ec.edu.unl.pawsity.dominio.mascota;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vacunas")
@NamedQueries({
        @NamedQuery(name = "Vacuna.findById", query = "SELECT v FROM Vacuna v WHERE v.id = :id"),
        @NamedQuery(name = "Vacuna.findByNombre", query = "SELECT v FROM Vacuna v WHERE LOWER(v.nombre) = LOWER(:nombre)"),
        @NamedQuery(name = "Vacuna.findByHistorial", query = "SELECT v FROM Vacuna v WHERE v.historialMedico.id = :idHistorial")
})
public class Vacuna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacuna")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    @Column(name = "fecha_proxima_dosis")
    private LocalDate fechaProximaDosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")
    private HistorialMedico historialMedico;

    public Vacuna() {
    }

    public Vacuna(Long id, @NotNull @NotEmpty String nombre, @NotNull LocalDate fechaAplicacion,
                  LocalDate fechaProximaDosis, HistorialMedico historialMedico) {
        this();
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la vacuna es requerido");
        this.fechaAplicacion = Objects.requireNonNull(fechaAplicacion, "La fecha de aplicación es requerida");
        this.fechaProximaDosis = fechaProximaDosis;
        this.historialMedico = historialMedico;
    }

    public Vacuna(String nombre, LocalDate fechaAplicacion, LocalDate fechaProximaDosis) {
        this(null, nombre, fechaAplicacion, fechaProximaDosis, null);
    }

    public void obtenerDetalles() {
        System.out.println("  - Vacuna: " + nombre + " | Fecha de aplicación: " + fechaAplicacion + " | Próxima dosis: " + fechaProximaDosis);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // Método de compatibilidad para lecturas en vistas JSF / PrimeFaces
    public LocalDate getFechaProximaAplicacion() {
        return fechaProximaDosis;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getFechaAplicacion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacuna vacuna = (Vacuna) o;
        return Objects.equals(getId(), vacuna.getId()) &&
                Objects.equals(getNombre(), vacuna.getNombre()) &&
                Objects.equals(getFechaAplicacion(), vacuna.getFechaAplicacion());
    }

    @Override
    public String toString() {
        return "Vacuna{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaAplicacion=" + fechaAplicacion +
                ", fechaProximaDosis=" + fechaProximaDosis +
                '}';
    }
}
