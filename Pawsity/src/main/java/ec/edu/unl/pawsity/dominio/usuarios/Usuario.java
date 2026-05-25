package ec.edu.unl.pawsity.dominio.usuarios;

public abstract class Usuario {
    protected String correoElectronico;
    protected String contrasena;

    public Usuario() {
    }

    public Usuario(String correoElectronico, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public void iniciarSesion() {
        System.out.println("Iniciando sesión en el sistema...");
    }

    public void cerrarSesion() {
        System.out.println("Sesión cerrada.");
    }

    public void actualizarDatos() {
        System.out.println("Actualizando datos del usuario...");
    }

    // Getters y Setters
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}