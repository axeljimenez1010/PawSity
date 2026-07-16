package ec.edu.unl.pawsity.servicios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.*;
import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("refugioService")
@ApplicationScoped
public class RefugioService {
    private Refugio refugio;
    private List<SolicitudDeAdopcion> sistemaDeSolicitudes;
    private List<Usuario> baseDeDatosUsuarios;

    @PostConstruct
    public void init() {
        this.refugio = new Refugio("PawSity Central", 50);
        this.sistemaDeSolicitudes = new ArrayList<>();
        this.baseDeDatosUsuarios = new ArrayList<>();

        // Usuarios semilla actualizados (Solo nombre, sin títulos ni apellidos)
        // Estructura: (correo, contrasena, nombres, apellidos, ...)
        baseDeDatosUsuarios.add(new Administrador("admin@mail.com", "123", "Laura", "", "Rescue Admin"));
        baseDeDatosUsuarios.add(new Veterinario("vet@mail.com", "123", "Mario", "", "Cirujano", "LIC-01"));
        baseDeDatosUsuarios.add(new Adoptante("carlos@mail.com", "123", "Carlos", "", "099", "Centro", "Docente"));

        // Mascotas semilla
        try {
            refugio.registrarMascota(new Mascota("Luna", "Canino", 2.5, "Mediano", "Hembra", "Marrón claro", EstadoMascota.DISPONIBLE));
            refugio.registrarMascota(new Mascota("Max", "Felino", 4.0, "Pequeño", "Macho", "Negro", EstadoMascota.DISPONIBLE));
            refugio.registrarMascota(new Mascota("Oliver", "Canino", 0.8, "Pequeño", "Macho", "Blanco y Canela", EstadoMascota.DISPONIBLE));
        } catch (Exception e) {}
    }

    public Usuario autenticar(String correo, String pass) {
        if (correo == null || pass == null) return null;
        return baseDeDatosUsuarios.stream()
                .filter(u -> u.iniciarSesion(correo, pass)).findFirst().orElse(null);
    }

    public Refugio getRefugio() { return refugio; }
    public List<SolicitudDeAdopcion> getSistemaDeSolicitudes() { return sistemaDeSolicitudes; }
}

