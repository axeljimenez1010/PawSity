package ec.edu.unl.pawsity.dominio.usuarios;

import java.util.Date;

public class Adoptante extends Usuario {

    private String telefono;
    private String direccion;
    private String nivelExperiencia;

    public Adoptante() {
        super();
    }

    public Adoptante(int idUsuario, String nombreCompleto, String email, String contrasena, Date fechaRegistro,
                     String telefono, String direccion, String nivelExperiencia) {
        super(idUsuario, nombreCompleto, email, contrasena, fechaRegistro);
        this.telefono = telefono;
        this.direccion = direccion;
        this.nivelExperiencia = nivelExperiencia;
    }

    public void verDetalleMascota(int idMascota) {
        System.out.println("Mostrando ficha técnica de la mascota ID: " + idMascota);
    }

    public void contactarRefugio() {
        System.out.println("Iniciando canal de contacto con el refugio...");
    }

    public boolean enviarSolicitudAdopcion(int idMascota) {
        System.out.println("Procesando formulario de adopción para la mascota ID: " + idMascota);
        return true;
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNivelExperiencia() { return nivelExperiencia; }
    public void setNivelExperiencia(String nivelExperiencia) { this.nivelExperiencia = nivelExperiencia; }
}
