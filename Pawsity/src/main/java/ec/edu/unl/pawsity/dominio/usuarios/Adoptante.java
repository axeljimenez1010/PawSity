package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;

public class Adoptante extends Usuario {
    private String telefono;
    private String direccion;

    public Adoptante() {
        super();
    }

    public Adoptante(String correoElectronico, String contrasena, String nombres, String apellidos, String telefono, String direccion) {
        super(correoElectronico, contrasena, nombres, apellidos);
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public void buscarMascota() {
        System.out.println("Ejecutando búsqueda de mascotas disponibles...");
    }

    public void enviarSolicitud(SolicitudDeAdopcion solicitud) {
        System.out.println("Enviando solicitud de adopción...");
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
