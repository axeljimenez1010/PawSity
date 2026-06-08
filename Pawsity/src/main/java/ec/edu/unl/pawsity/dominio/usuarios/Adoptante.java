package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import java.util.ArrayList;
import java.util.List;

public class Adoptante extends Usuario {
    private String telefono;
    private String direccion;
    private String ocupacion;

    public Adoptante(String correo, String contrasena, String nombres, String apellidos, String telefono, String direccion, String ocupacion) {
        super(correo, contrasena, nombres, apellidos);
        this.telefono = telefono;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
    }

    public void enviarSolicitud(Mascota mascotaDeseada) {
        System.out.println("Solicitud enviada para adoptar a: " + mascotaDeseada.getNombre());
    }

    public List<Mascota> buscarMascota(String especie, String tamano, double edad) {
        return new ArrayList<>();
    }

    @Override
    public void redireccionarPanel() {
        System.out.println(">> Abriendo Catálogo de Adopciones para ADOPTANTE...");
    }
}
