package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;
import jakarta.persistence.*; // ⭐ IMPORTANTE: Importaciones de Jakarta EE

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "administradores")

@PrimaryKeyJoinColumn(name = "id_usuario")
public class Administrador extends Usuario {


    @Column(name = "puesto", length = 80)
    private String puesto;


    protected Administrador() {
        super();
    }

    public Administrador(String correo, String contrasena, String nombres, String apellidos, String puesto) {
        super(correo, contrasena, nombres, apellidos);
        this.puesto = puesto;
    }

    public void gestionarSolicitud(SolicitudDeAdopcion solicitud, boolean estadoAprobacion) {
        if (estadoAprobacion) {
            solicitud.aprobar();
        } else {
            solicitud.rechazar();
            System.out.println("La solicitud ha sido rechazada. La mascota se encuentra disponible nuevamente.");
        }
    }

    public void actualizarCatalogo(Refugio refugio, Mascota mascota) throws CapacidadRefugioExcedidaException {
        refugio.registrarMascota(mascota);
        System.out.println("El catálogo ha sido actualizado. Se ha registrado a: " + mascota.getNombre());
    }

    @Override
    public void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes) {
        int opcion = 0;
        do {
            System.out.println("\n--- Panel Administrativo: " + this.nombres + " ---");
            System.out.println("1. Registrar ingreso de mascota");
            System.out.println("2. Revisar solicitudes de adopción (" + solicitudes.size() + " pendientes)");
            System.out.println("3. Ver censo de mascotas");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la mascota: ");
                    String nombre = sc.nextLine();
                    System.out.print("Especie: ");
                    String especie = sc.nextLine();
                    System.out.print("Edad estimada: ");
                    double edad = Double.parseDouble(sc.nextLine());
                    Mascota nueva = new Mascota(nombre, especie, edad, "Mediano", "Desconocido", "Mestizo", EstadoMascota.DISPONIBLE);
                    try {
                        this.actualizarCatalogo(refugio, nueva);
                    } catch (CapacidadRefugioExcedidaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    if (solicitudes.isEmpty()) {
                        System.out.println("No hay solicitudes pendientes de revisión.");
                        break;
                    }
                    SolicitudDeAdopcion s = solicitudes.get(0);
                    System.out.println("Solicitud de adopción enviada por " + s.getAdoptante().getNombres() + " para la mascota " + s.getMascota().getNombre() + ".");
                    System.out.print("¿Aprobar solicitud? (si/no): ");
                    boolean aprobar = sc.nextLine().equalsIgnoreCase("si");
                    this.gestionarSolicitud(s, aprobar);
                    solicitudes.remove(s);
                    break;
                case 3:
                    System.out.println("\nRegistro histórico de mascotas ingresadas: " + Mascota.getTotalMascotasRescatadas());
                    System.out.println("Mascotas actualmente en el refugio:");
                    for (Mascota m : refugio.buscarMascota()) {
                        System.out.println("  - " + m.getNombre() + " (" + m.getEspecie() + ") | Estado: " + m.getEstado());
                    }
                    break;
            }
        } while (opcion != 4);
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}