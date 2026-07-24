package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import jakarta.persistence.*; // ⭐ IMPORTANTE: El paquete oficial de Jakarta EE
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "usuarios")

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;


    @Column(name = "correo_electronico", unique = true, nullable = false, length = 100)
    protected String correoElectronico;

    @Column(name = "contrasena", nullable = false, length = 64)
    protected String contrasena;

    @Column(name = "nombres", nullable = false, length = 80)
    protected String nombres;

    @Column(name = "apellidos", nullable = false, length = 80)
    protected String apellidos;

    protected Usuario() {
    }

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

