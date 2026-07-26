package ec.edu.unl.pawsity.dominio.mascota;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "historiales_medicos")
@NamedQueries({
        @NamedQuery(name = "HistorialMedico.findById", query = "SELECT h FROM HistorialMedico h WHERE h.id = :id"),
        @NamedQuery(name = "HistorialMedico.findAll", query = "SELECT h FROM HistorialMedico h")
})
public class HistorialMedico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long id;


    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;


    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Vacuna> vacunas;


    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ConsultaMedica> consultasMedicas;


    @OneToOne(mappedBy = "historialMedico", fetch = FetchType.LAZY)
    private Mascota mascota;

    public HistorialMedico() {
        this.fechaCreacion = LocalDate.now();
        // Inicialización obligatoria para prevenir NullPointerException al crear un historial nuevo
        this.vacunas = new ArrayList<>();
        this.consultasMedicas = new ArrayList<>();
    }

    public HistorialMedico(Long id, @NotNull LocalDate fechaCreacion) {
        this();
        this.id = id;
        this.fechaCreacion = Objects.requireNonNull(fechaCreacion, "La fecha de creación es requerida");
    }


    public void agregarVacuna(Vacuna nuevaVacuna) {
        Objects.requireNonNull(nuevaVacuna, "La vacuna a registrar es requerida");
        this.vacunas.add(nuevaVacuna);
        nuevaVacuna.setHistorialMedico(this);
    }

    public void registrarConsulta(ConsultaMedica nuevaConsulta) {
        Objects.requireNonNull(nuevaConsulta, "La consulta médica es requerida");
        this.consultasMedicas.add(nuevaConsulta);
        nuevaConsulta.setHistorialMedico(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Vacuna> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<Vacuna> vacunas) {
        this.vacunas = vacunas;
    }

    public List<ConsultaMedica> getConsultasMedicas() {
        return consultasMedicas;
    }

    public void setConsultasMedicas(List<ConsultaMedica> consultasMedicas) {
        this.consultasMedicas = consultasMedicas;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFechaCreacion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialMedico that = (HistorialMedico) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFechaCreacion(), that.getFechaCreacion());
    }

    @Override
    public String toString() {
        return "HistorialMedico{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
