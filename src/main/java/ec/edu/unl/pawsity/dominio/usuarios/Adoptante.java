package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import jakarta.persistence.*; // ⭐ IMPORTANTE: Importaciones del estándar Jakarta EE

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "adoptantes")

@PrimaryKeyJoinColumn(name = "id_usuario")
public class Adoptante extends Usuario {

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "ocupacion", length = 80)
    private String ocupacion;

    protected Adoptante() {
        super();
    }

    public Adoptante(String correo, String contrasena, String nombres, String apellidos, String telefono, String direccion, String ocupacion) {
        super(correo, contrasena, nombres, apellidos);
        this.telefono = telefono;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
    }

    public void enviarSolicitud(Mascota mascotaDeseada, List<SolicitudDeAdopcion> bandejaGlobal) {
        System.out.println("Su solicitud para adoptar a " + mascotaDeseada.getNombre() + " ha sido enviada con éxito.");
        mascotaDeseada.setEstado(EstadoMascota.EN_PROCESO);
        bandejaGlobal.add(new SolicitudDeAdopcion(this, mascotaDeseada));
    }

    public List<Mascota> buscarMascota(String especie, Refugio refugio) {
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

            try { opcion = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { continue; }

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
                int seleccion = Integer.parseInt(sc.nextLine());
                if (seleccion > 0 && seleccion <= catalogo.size()) {
                    Mascota elegida = catalogo.get(seleccion - 1);
                    this.enviarSolicitud(elegida, solicitudes);
                    System.out.println("La solicitud se encuentra en revisión por parte de la administración.");
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
}
