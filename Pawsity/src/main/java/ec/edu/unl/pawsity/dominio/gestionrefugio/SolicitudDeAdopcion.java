package ec.edu.unl.pawsity.dominio.gestionrefugio;

import ec.edu.unl.pawsity.dominio.mascota.MainPet;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import java.util.Date;

public class SolicitudDeAdopcion {

    private Date fechaSolicitud;
    private EstadoSolicitud estado;
    private Date fechaAdopcion;
    private String estadoFinal;

    private Adoptante adoptante;
    private MainPet mascota;


    public SolicitudDeAdopcion(Date fechaSolicitud, Adoptante adoptante, MainPet mascota) {
        this.fechaSolicitud = fechaSolicitud;
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public void cancelar() {
        if (this.estado == EstadoSolicitud.APROBADO) {
            throw new IllegalStateException("No se puede cancelar una solicitud que ya ha sido aprobada.");
        }
        this.estado = EstadoSolicitud.RECHAZADA;
        this.estadoFinal = "Cancelada por el adoptante/sistema";
    }

    // Getters y Setters
    public Date getFechaSolicitud() { return fechaSolicitud; }
    public EstadoSolicitud getEstado() { return estado; }
    public Date getFechaAdopcion() { return fechaAdopcion; }
    public String getEstadoFinal() { return estadoFinal; }
    public Adoptante getAdoptante() { return adoptante; }
    public MainPet getMascota() { return mascota; }

    // Comportamiento del dominio: Para aprobar la solicitud
    public void aprobar(Date fechaAdopcion, String comentarioFinal) {
        this.estado = EstadoSolicitud.APROBADO;
        this.fechaAdopcion = fechaAdopcion;
        this.estadoFinal = comentarioFinal;
    }
}