package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Scanner;

public abstract class Usuario {
    protected String correoElectronico;
    protected String contrasena;
    protected String nombres;
    protected String apellidos;

    public Usuario(String correoElectronico, String contrasena, String nombres, String apellidos) {
        this.correoElectronico = correoElectronico;
        this.contrasena = hash(contrasena);
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public boolean iniciarSesion(String correoIngresado, String passIngresado) {
        return this.correoElectronico.equals(correoIngresado) && this.contrasena.equals(hash(passIngresado));
    }

    protected static String hash(String textoPlano) {
        if (textoPlano == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(textoPlano.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo de hash no disponible", e);
        }
    }

    public abstract void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes);

    public String getNombres() {
        return nombres;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
}
