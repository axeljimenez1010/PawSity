package ec.edu.unl.pawsity.dominio.usuarios;

public abstract class Usuario {
    protected String correoElectronico;
    protected String contrasena;
    protected String nombres;
    protected String apellidos;

    public Usuario() {
    }

    public Usuario(String correoElectronico, String contrasena, String nombres, String apellidos) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public boolean iniciarSesion() {
        System.out.println("Iniciando sesión en el sistema...");
        return true;
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

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
}

