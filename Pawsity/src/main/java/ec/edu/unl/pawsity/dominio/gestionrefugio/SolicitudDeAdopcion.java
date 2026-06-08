package ec.edu.unl.pawsity.dominio.gestionrefugio;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import java.time.LocalDate;

public class SolicitudDeAdopcion {
    private LocalDate fechaSolicitud;
    private EstadoSolicitud estado;
    private LocalDate fechaAdopcion;
    private String estadoFinal;

    private Adoptante adoptante;
    private Mascota mascota;

    public SolicitudDeAdopcion(Adoptante adoptante, Mascota mascota) {
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public void aprobar() {
        this.estado = EstadoSolicitud.APROBADO;
        this.mascota.setEstado(EstadoMascota.ADOPTADO);
        this.fechaAdopcion = LocalDate.now();
        System.out.println("Solicitud aprobada con éxito.");
    }
}