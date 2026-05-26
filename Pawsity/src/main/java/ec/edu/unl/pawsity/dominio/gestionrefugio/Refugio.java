package ec.edu.unl.pawsity.dominio.gestionrefugio;

import ec.edu.unl.pawsity.dominio.mascota.Pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Refugio {

    private String nombre;
    private String direccion;
    private String horarios;
    private String telefono;
    private String correo;

    // Relación: Un refugio contiene múltiples mascotas 
    private final List<Pet> mascotas;

    public Refugio(String nombre, String direccion, String horarios, String telefono, String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.horarios = horarios;
        this.telefono = telefono;
        this.correo = correo;
        this.mascotas = new ArrayList<>();
    }

    public List<Pet> buscarMascota() {
        return Collections.unmodifiableList(this.mascotas);
    }

    // Comportamiento para gestionar la relación con las mascotas
    public void registrarMascota(Pet mascota) {
        if (mascota != null) {
            this.mascotas.add(mascota);
        }
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getHorarios() { return horarios; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
}
