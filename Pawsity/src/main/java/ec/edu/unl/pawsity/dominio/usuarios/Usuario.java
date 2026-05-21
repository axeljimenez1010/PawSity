package ec.edu.unl.pawsity.dominio.usuarios;

import java.util.Date;

public abstract class Usuario {

    private int idUsuario;
    private String nombreCompleto;
    private String email;
    private String contrasena;
    private Date fechaRegistro;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreCompleto, String email, String contrasena, Date fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
    }

    public boolean iniciarSesion(String email, String contrasena) {
        return this.email != null && this.email.equals(email) &&
                this.contrasena != null && this.contrasena.equals(contrasena);
    }

    public void cerrarSesion() {
        System.out.println("Sesión finalizada para: " + this.nombreCompleto);
    }

    public void actualizarDatos() {
        System.out.println("Datos de perfil actualizados en el sistema.");
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}