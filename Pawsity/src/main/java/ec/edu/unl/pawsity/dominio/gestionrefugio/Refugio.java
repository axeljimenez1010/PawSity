package ec.edu.unl.pawsity.dominio.gestionrefugio;

public class Refugio {

    private String nombre;
    private String direccion;
    private String horarios;
    private String telefono;
    private String correo;

    // Constructor para inicializar el Refugio
    public Refugio(String nombre, String direccion, String horarios, String telefono, String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.horarios = horarios;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
