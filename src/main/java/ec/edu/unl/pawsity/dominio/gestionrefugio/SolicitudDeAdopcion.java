package ec.edu.unl.pawsity.dominio.gestionrefugio;

import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "solicitudes_adopcion")
@NamedQueries({
        @NamedQuery(name = "SolicitudDeAdopcion.findAll", query = "SELECT s FROM SolicitudDeAdopcion s"),
        @NamedQuery(name = "SolicitudDeAdopcion.findByEstado", query = "SELECT s FROM SolicitudDeAdopcion s WHERE s.estado = :estado"),
        @NamedQuery(name = "SolicitudDeAdopcion.findByAdoptante", query = "SELECT s FROM SolicitudDeAdopcion s WHERE s.adoptante.id = :idAdoptante"),
        @NamedQuery(name = "SolicitudDeAdopcion.findByMascota", query = "SELECT s FROM SolicitudDeAdopcion s WHERE s.mascota.id = :idMascota")
})
public class SolicitudDeAdopcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;

    @NotNull
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoSolicitud estado;

    @Column(name = "fecha_adopcion")
    private LocalDate fechaAdopcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_adoptante", nullable = false)
    private Adoptante adoptante;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @Column(name = "tipo_vivienda", length = 100)
    private String tipoVivienda;

    @Column(name = "tiene_otras_mascotas")
    private boolean tieneOtrasMascotas;

    @Column(name = "experiencia_previa", length = 500)
    private String experienciaPrevia;

    @Column(name = "motivo_adopcion", length = 1000)
    private String motivoAdopcion;

    public SolicitudDeAdopcion() {
    }

    public SolicitudDeAdopcion(Long id, @NotNull Adoptante adoptante, @NotNull Mascota mascota) {
        this();
        this.id = id;
        this.adoptante = Objects.requireNonNull(adoptante, "El adoptante es requerido para iniciar la solicitud");
        this.mascota = Objects.requireNonNull(mascota, "La mascota es requerida para iniciar la solicitud");
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public SolicitudDeAdopcion(Adoptante adoptante, Mascota mascota) {
        this(null, adoptante, mascota);
    }


    public void registrarFormulario(String tipoVivienda, boolean tieneOtrasMascotas, String experienciaPrevia, String motivoAdopcion) {
        this.tipoVivienda = tipoVivienda;
        this.tieneOtrasMascotas = tieneOtrasMascotas;
        this.experienciaPrevia = experienciaPrevia;
        this.motivoAdopcion = motivoAdopcion;
    }

    public void aprobar() {
        this.estado = EstadoSolicitud.APROBADO;
        if (this.mascota != null) {
            this.mascota.setEstado(EstadoMascota.ADOPTADO);
        }
        this.fechaAdopcion = LocalDate.now();
        System.out.println("El trámite ha finalizado. La mascota ha sido adoptada oficialmente.");
    }

    public void rechazar() {
        this.estado = EstadoSolicitud.RECHAZADA;
        if (this.mascota != null) {
            this.mascota.setEstado(EstadoMascota.DISPONIBLE);
        }
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(LocalDate fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante adoptante) { this.adoptante = adoptante; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }

    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public void setTieneOtrasMascotas(boolean tieneOtrasMascotas) { this.tieneOtrasMascotas = tieneOtrasMascotas; }

    public String getExperienciaPrevia() { return experienciaPrevia; }
    public void setExperienciaPrevia(String experienciaPrevia) { this.experienciaPrevia = experienciaPrevia; }

    public String getMotivoAdopcion() { return motivoAdopcion; }
    public void setMotivoAdopcion(String motivoAdopcion) { this.motivoAdopcion = motivoAdopcion; }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFechaSolicitud(), getEstado());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudDeAdopcion that = (SolicitudDeAdopcion) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFechaSolicitud(), that.getFechaSolicitud()) &&
                getEstado() == that.getEstado();
    }

    @Override
    public String toString() {
        return "SolicitudDeAdopcion{" +
                "id=" + id +
                ", fechaSolicitud=" + fechaSolicitud +
                ", estado=" + estado +
                ", fechaAdopcion=" + fechaAdopcion +
                '}';
    }
}