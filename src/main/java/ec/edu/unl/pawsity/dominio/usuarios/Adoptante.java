package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "adoptantes")
@PrimaryKeyJoinColumn(name = "id_usuario")
@NamedQueries({
        @NamedQuery(name = "Adoptante.findByTelefono", query = "SELECT a FROM Adoptante a WHERE a.telefono = :telefono"),
        @NamedQuery(name = "Adoptante.findByOcupacion", query = "SELECT a FROM Adoptante a WHERE LOWER(a.ocupacion) = LOWER(:ocupacion)")
})
public class Adoptante extends Usuario implements Serializable {

    @NotNull
    @NotEmpty
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @NotNull
    @NotEmpty
    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @NotNull
    @NotEmpty
    @Column(name = "ocupacion", nullable = false, length = 80)
    private String ocupacion;

    public Adoptante() {
        super();
    }

    public Adoptante(Long id, @NotNull @NotEmpty String correo, @NotNull @NotEmpty String contrasena,
                     @NotNull @NotEmpty String nombres, @NotNull @NotEmpty String apellidos,
                     @NotNull @NotEmpty String telefono, @NotNull @NotEmpty String direccion,
                     @NotNull @NotEmpty String ocupacion) {
        super(id, correo, contrasena, nombres, apellidos);
        this.telefono = Objects.requireNonNull(telefono, "El teléfono es requerido");
        this.direccion = Objects.requireNonNull(direccion, "La dirección es requerida");
        this.ocupacion = Objects.requireNonNull(ocupacion, "La ocupación es requerida");
    }

    public Adoptante(String correo, String contrasena, String nombres, String apellidos,
                     String telefono, String direccion, String ocupacion) {
        this(0L, correo, contrasena, nombres, apellidos, telefono, direccion, ocupacion);
    }

    public void enviarSolicitud(Mascota mascotaDeseada, List<SolicitudDeAdopcion> bandejaGlobal) {
        Objects.requireNonNull(mascotaDeseada, "La mascota deseada es requerida");
        Objects.requireNonNull(bandejaGlobal, "La bandeja global de solicitudes es requerida");

        System.out.println("Su solicitud para adoptar a " + mascotaDeseada.getNombre() + " ha sido enviada con éxito.");
        mascotaDeseada.setEstado(EstadoMascota.EN_PROCESO);
        bandejaGlobal.add(new SolicitudDeAdopcion(this, mascotaDeseada));
    }

    public List<Mascota> buscarMascota(String especie, Refugio refugio) {
        Objects.requireNonNull(especie, "La especie para buscar es requerida");
        Objects.requireNonNull(refugio, "El refugio es requerido");

        List<Mascota> resultados = new ArrayList<>();
        for (Mascota m : refugio.buscarMascota()) {
            if (m.getEspecie().equalsIgnoreCase(especie) && m.getEstado() == EstadoMascota.DISPONIBLE) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    @Override
    public void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes) {
        int opcion = 0;
        do {
            System.out.println("\n--- Catálogo de Adopciones: " + this.nombres + " ---");
            System.out.println("1. Ver lista de todas las mascotas disponibles");
            System.out.println("2. Buscar mascota por especie");
            System.out.println("3. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            if (opcion == 1 || opcion == 2) {
                List<Mascota> catalogo = new ArrayList<>();

                if (opcion == 1) {
                    for (Mascota m : refugio.buscarMascota()) {
                        if (m.getEstado() == EstadoMascota.DISPONIBLE) catalogo.add(m);
                    }
                } else {
                    System.out.print("Ingrese la especie que desea buscar (Ej. Canino, Felino): ");
                    catalogo = this.buscarMascota(sc.nextLine(), refugio);
                }

                if (catalogo.isEmpty()) {
                    System.out.println("Lo sentimos, no hay mascotas con esas características disponibles en este momento.");
                    continue;
                }

                System.out.println("\nMascotas disponibles para adopción:");
                for (int i = 0; i < catalogo.size(); i++) {
                    System.out.println((i + 1) + ". " + catalogo.get(i).getNombre() + " (" + catalogo.get(i).getEspecie() + ")");
                }

                System.out.print("\nIngrese el número de la mascota que desea adoptar (o presione 0 para cancelar): ");
                try {
                    int seleccion = Integer.parseInt(sc.nextLine());
                    if (seleccion > 0 && seleccion <= catalogo.size()) {
                        Mascota elegida = catalogo.get(seleccion - 1);
                        this.enviarSolicitud(elegida, solicitudes);
                        System.out.println("La solicitud se encuentra en revisión por parte de la administración.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida. Operación cancelada.");
                }
            }
        } while (opcion != 3);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Adoptante adoptante = (Adoptante) o;
        return Objects.equals(getTelefono(), adoptante.getTelefono()) &&
                Objects.equals(getDireccion(), adoptante.getDireccion()) &&
                Objects.equals(getOcupacion(), adoptante.getOcupacion());
    }

    @Override
    public String toString() {
        return "Adoptante{" +
                "id=" + getId() +
                ", correoElectronico='" + getCorreoElectronico() + '\'' +
                ", nombres='" + getNombres() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                '}';
    }
}