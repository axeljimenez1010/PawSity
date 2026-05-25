package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.mascota.MainPet;

public class Adoptante extends Usuario {
    private String telefono;
    private String direccion;
    private String nombres;
    private String apellidos;

    public Adoptante() {
        super();
    }

    public Adoptante(String correoElectronico, String contrasena, String telefono, String direccion, String nombres, String apellidos) {
        super(correoElectronico, contrasena);
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public void buscarMascota() {
        System.out.println("Ejecutando búsqueda de mascotas disponibles...");
    }

    public void enviarSolicitud(MainPet m) {
        System.out.println("Enviando solicitud de adopción...");
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
}
