package ec.edu.unl.pawsity.dominio.usuarios;

public abstract class Usuario {

    // El símbolo # en UML significa protected
    protected String correoElectronico;
    protected String contrasena;

    public Usuario() {
    }

    public Usuario(String correoElectronico, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public boolean autenticar() {
        System.out.println("Autenticando credenciales en el sistema...");
        return true;
    }

    // Getters y Setters
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}