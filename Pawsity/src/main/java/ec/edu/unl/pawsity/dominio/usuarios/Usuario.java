package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import java.util.List;
import java.util.Scanner;

public abstract class Usuario {
    protected String correoElectronico;
    protected String contrasena;
    protected String nombres;
    protected String apellidos;

    public Usuario(String correoElectronico, String contrasena, String nombres, String apellidos) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public boolean iniciarSesion(String correoIngresado, String passIngresado) {
        return this.correoElectronico.equals(correoIngresado) && this.contrasena.equals(passIngresado);
    }

    // Método polimórfico interactivo
    public abstract void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes);

    public String getNombres() { return nombres; }
}

