package ec.edu.unl.pawsity.dominio.mascota;

import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "consultas_medicas")
@NamedQueries({
        @NamedQuery(name = "ConsultaMedica.findById", query = "SELECT c FROM ConsultaMedica c WHERE c.id = :id"),
        @NamedQuery(name = "ConsultaMedica.findByVeterinario", query = "SELECT c FROM ConsultaMedica c WHERE c.veterinarioEncargado.id = :idVet"),
        @NamedQuery(name = "ConsultaMedica.findByFecha", query = "SELECT c FROM ConsultaMedica c WHERE c.fechaDiagnostico = :fecha")
})
public class ConsultaMedica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    @NotNull
    @Column(name = "fecha_diagnostico", nullable = false)
    private LocalDate fechaDiagnostico;

    @NotNull
    @NotEmpty
    @Column(name = "diagnostico", nullable = false, length = 500)
    private String diagnostico;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_veterinario", nullable = false)
    private Veterinario veterinarioEncargado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")
    private HistorialMedico historialMedico;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "consulta_medicamentos", joinColumns = @JoinColumn(name = "id_consulta"))
    @Column(name = "medicamento", length = 150)
    private List<String> medicamentosAplicados;

    public ConsultaMedica() {
        this.fechaDiagnostico = LocalDate.now();
        this.medicamentosAplicados = new ArrayList<>();
    }

    public ConsultaMedica(Long id, @NotNull LocalDate fechaDiagnostico, @NotNull @NotEmpty String diagnostico,
                          @NotNull Veterinario veterinarioEncargado, HistorialMedico historialMedico) {
        this();
        this.id = id;
        this.fechaDiagnostico = Objects.requireNonNull(fechaDiagnostico, "La fecha de diagnóstico es requerida");
        this.diagnostico = Objects.requireNonNull(diagnostico, "El diagnóstico es requerido");
        this.veterinarioEncargado = Objects.requireNonNull(veterinarioEncargado, "El veterinario encargado es requerido");
        this.historialMedico = historialMedico;
    }

    public ConsultaMedica(LocalDate fechaDiagnostico, String diagnostico, Veterinario veterinarioEncargado) {
        this(null, fechaDiagnostico, diagnostico, veterinarioEncargado, null);
    }


    public void agregarMedicamento(String medicamento) {
        Objects.requireNonNull(medicamento, "El medicamento a registrar no puede ser nulo");
        this.medicamentosAplicados.add(medicamento);
    }

    public String detallesConsulta() {
        String nombreVet = (veterinarioEncargado != null) ? veterinarioEncargado.getNombres() : "No asignado";
        return "Fecha: " + fechaDiagnostico + " - Dr(a): " + nombreVet + " | Diagnóstico: " + diagnostico;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(LocalDate fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<String> getMedicamentosAplicados() {
        return medicamentosAplicados;
    }

    public void setMedicamentosAplicados(List<String> medicamentosAplicados) {
        this.medicamentosAplicados = medicamentosAplicados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Veterinario getVeterinarioEncargado() {
        return veterinarioEncargado;
    }

    public void setVeterinarioEncargado(Veterinario veterinarioEncargado) {
        this.veterinarioEncargado = veterinarioEncargado;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFechaDiagnostico(), getDiagnostico());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaMedica that = (ConsultaMedica) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFechaDiagnostico(), that.getFechaDiagnostico()) &&
                Objects.equals(getDiagnostico(), that.getDiagnostico());
    }

    @Override
    public String toString() {
        return "ConsultaMedica{" +
                "id=" + id +
                ", fechaDiagnostico=" + fechaDiagnostico +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}

