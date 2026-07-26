package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE LOWER(u.correoElectronico) = LOWER(:correo)"),
        @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
        @NamedQuery(name = "Usuario.findLikeNombres", query = "SELECT u FROM Usuario u WHERE LOWER(u.nombres) LIKE LOWER(:nombres)")
})
public abstract class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "correo_electronico", unique = true, nullable = false, length = 100)
    protected String correoElectronico;

    @NotNull
    @NotEmpty
    @Column(name = "contrasena", nullable = false, length = 64)
    protected String contrasena;

    @NotNull
    @NotEmpty
    @Column(name = "nombres", nullable = false, length = 80)
    protected String nombres;

    @NotNull
    @NotEmpty
    @Column(name = "apellidos", nullable = false, length = 80)
    protected String apellidos;

    protected Usuario() {
    }

    public Usuario(Long id, @NotNull @NotEmpty String correoElectronico, @NotNull @NotEmpty String contrasena,
                   @NotNull @NotEmpty String nombres, @NotNull @NotEmpty String apellidos) {
        this();
        this.id = Objects.requireNonNull(id, "El id es requerido");
        this.correoElectronico = Objects.requireNonNull(correoElectronico, "El correo electrónico es requerido");
        this.contrasena = hash(Objects.requireNonNull(contrasena, "La contraseña es requerida"));
        this.nombres = Objects.requireNonNull(nombres, "Los nombres son requeridos");
        this.apellidos = Objects.requireNonNull(apellidos, "Los apellidos son requeridos");
    }

    public Usuario(String correoElectronico, String contrasena, String nombres, String apellidos) {
        this(0L, correoElectronico, contrasena, nombres, apellidos);
    }

    public boolean iniciarSesion(String correoIngresado, String passIngresado) {
        if (correoIngresado == null || passIngresado == null) return false;

        String correoLimpio = correoIngresado.trim();
        String passLimpio = passIngresado.trim();

        return this.correoElectronico.equalsIgnoreCase(correoLimpio) &&
                this.contrasena.equals(hash(passLimpio));
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

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCorreoElectronico());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId()) &&
                Objects.equals(getCorreoElectronico(), usuario.getCorreoElectronico());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }
}

