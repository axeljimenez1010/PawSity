package ec.edu.unl.pawsity.dominio.mascota;

import java.util.Date;

public class MedicalHistory {

    private Date fechaCreacion;
    private String ultimaVacuna;
    private String tratamientoActual;
    private String observacion;

    public MedicalHistory(Date fechaCreacion, String ultimaVacuna, String tratamientoActual, String observacion) {
        this.fechaCreacion = fechaCreacion;
        this.ultimaVacuna = ultimaVacuna;
        this.tratamientoActual = tratamientoActual;
        this.observacion = observacion;
    }

    public void actualizarHistorial() {
        // TODO: Implementar la lógica para actualizar el historial
        System.out.println("Historial médico actualizado.");
    }

    // Getters y Setters
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getUltimaVacuna() { return ultimaVacuna; }
    public void setUltimaVacuna(String ultimaVacuna) { this.ultimaVacuna = ultimaVacuna; }

    public String getTratamientoActual() { return tratamientoActual; }
    public void setTratamientoActual(String tratamientoActual) { this.tratamientoActual = tratamientoActual; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

}
