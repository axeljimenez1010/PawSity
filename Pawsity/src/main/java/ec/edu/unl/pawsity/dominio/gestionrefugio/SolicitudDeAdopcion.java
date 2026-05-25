package ec.edu.unl.pawsity.dominio.gestionrefugio;

import java.util.Date;

public class SolicitudDeAdopcion {

    private Date fechaSolicitud;
    private EstadoSolicitud estado;
    private Date fechaAdopcion;
    private String estadoFinal;

    // Constructor
    public SolicitudDeAdopcion(Date fechaSolicitud, EstadoSolicitud estado, Date fechaAdopcion, String estadoFinal) {
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.fechaAdopcion = fechaAdopcion;
        this.estadoFinal = estadoFinal;
    }

    // Operación definida en el diagrama UML
    public void cancelar() {
        this.estado = EstadoSolicitud.RECHAZADA;
        this.estadoFinal = "Cancelada por el usuario";
        System.out.println("La solicitud ha sido cancelada exitosamente.");
    }

    // Getters y Setters
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Date getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(Date fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
}
