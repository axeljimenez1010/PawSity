package ec.edu.unl.pawsity.dominio.usuarios;

import java.util.Date;

public class Veterinario extends Usuario {

    private String numeroCredencial;
    private String especialidad;

    public Veterinario() {
        super();
    }

    public Veterinario(int idUsuario, String nombreCompleto, String email, String contrasena, Date fechaRegistro,
                       String numeroCredencial, String especialidad) {
        super(idUsuario, nombreCompleto, email, contrasena, fechaRegistro);
        this.numeroCredencial = numeroCredencial;
        this.especialidad = especialidad;
    }

    public void registrarMascota(Object datosMascota) {
        System.out.println("Ficha de ingreso médico creada.");
    }

    public void actualizarExpediente(int idExpediente) {
        System.out.println("Historial clínico modificado para el expediente: " + idExpediente);
    }

    public void registrarTratamiento(int idMascota, Object datosTratamiento) {
        System.out.println("Nuevo tratamiento asignado a la mascota ID: " + idMascota);
    }

    // Getters y Setters
    public String getNumeroCredencial() { return numeroCredencial; }
    public void setNumeroCredencial(String numeroCredencial) { this.numeroCredencial = numeroCredencial; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}