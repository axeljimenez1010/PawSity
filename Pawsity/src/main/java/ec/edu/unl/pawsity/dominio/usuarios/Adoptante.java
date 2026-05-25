package ec.edu.unl.pawsity.dominio.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Adoptante extends Usuario {

    private String nombres;
    private String apellidos;
    private String telefono;

    public Adoptante() {
        super();
    }

    public Adoptante(String correoElectronico, String contrasena, String nombres, String apellidos, String telefono) {
        super(correoElectronico, contrasena);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    // TODO: Reemplazar Object por Mascota cuando el compañero suba su clase
    public List<Object> buscarMascota(String tamano, String color, Integer edad) {
        System.out.println("Buscando mascotas que coincidan con los filtros...");
        return new ArrayList<>();
    }

    // TODO: Reemplazar Object por Mascota
    public void enviarSolicitud(Object m) {
        System.out.println("Solicitud enviada para la mascota seleccionada.");
    }

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
