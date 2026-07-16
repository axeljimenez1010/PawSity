package ec.edu.unl.pawsity.dominio.gestionrefugio;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import java.time.LocalDate;

public class SolicitudDeAdopcion {
    private LocalDate fechaSolicitud;
    private EstadoSolicitud estado;
    private LocalDate fechaAdopcion;
    private Adoptante adoptante;
    private Mascota mascota;

    // Respuestas del formulario que el adoptante llena al solicitar la adopción
    private String tipoVivienda;
    private boolean tieneOtrasMascotas;
    private String experienciaPrevia;
    private String motivoAdopcion;

    public SolicitudDeAdopcion(Adoptante adoptante, Mascota mascota) {
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    // Se llama por separado tras el constructor para no romper los lugares
    // del código que ya crean SolicitudDeAdopcion sin estos datos (ej. flujo de consola).
    public void registrarFormulario(String tipoVivienda, boolean tieneOtrasMascotas, String experienciaPrevia, String motivoAdopcion) {
        this.tipoVivienda = tipoVivienda;
        this.tieneOtrasMascotas = tieneOtrasMascotas;
        this.experienciaPrevia = experienciaPrevia;
        this.motivoAdopcion = motivoAdopcion;
    }

    public void aprobar() {
        this.estado = EstadoSolicitud.APROBADO;
        this.mascota.setEstado(EstadoMascota.ADOPTADO);
        this.fechaAdopcion = LocalDate.now();
        System.out.println("El trámite ha finalizado. La mascota ha sido adoptada oficialmente.");
    }

    public void rechazar() {
        this.estado = EstadoSolicitud.RECHAZADA;
        this.mascota.setEstado(EstadoMascota.DISPONIBLE);
    }

    public Mascota getMascota() { return mascota; }
    public Adoptante getAdoptante() { return adoptante; }
    public EstadoSolicitud getEstado() { return estado; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    public String getTipoVivienda() { return tipoVivienda; }
    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public String getExperienciaPrevia() { return experienciaPrevia; }
    public String getMotivoAdopcion() { return motivoAdopcion; }
}