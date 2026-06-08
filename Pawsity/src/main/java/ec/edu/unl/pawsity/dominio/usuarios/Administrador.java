package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;

import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario {
    private String puesto;

    public Administrador(String correo, String contrasena, String nombres, String apellidos, String puesto) {
        super(correo, contrasena, nombres, apellidos);
        this.puesto = puesto;
    }

    public void gestionarSolicitud(SolicitudDeAdopcion solicitud, boolean estadoAprobacion) {
        if(estadoAprobacion) {
            solicitud.aprobar();
        } else {
            System.out.println(">> Solicitud rechazada. La mascota vuelve a estar disponible.");
            solicitud.getMascota().setEstado(EstadoMascota.DISPONIBLE);
        }
    }

    public void actualizarCatalogo(Refugio refugio, Mascota mascota) throws CapacidadRefugioExcedidaException {
        refugio.registrarMascota(mascota);
        System.out.println(">> Catálogo actualizado con: " + mascota.getNombre());
    }

    @Override
    public void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes) {
        int opcion = 0;
        do {
            System.out.println("\n=== PANEL ADMINISTRATIVO: " + this.nombres.toUpperCase() + " ===");
            System.out.println("1. Ingresar nueva mascota al sistema");
            System.out.println("2. Revisar Bandeja de Adopciones (" + solicitudes.size() + " pendientes)");
            System.out.println("3. Ver Censo Histórico");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione: ");

            try { opcion = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { continue; }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de mascota: "); String nombre = sc.nextLine();
                    System.out.print("Especie: "); String especie = sc.nextLine();
                    System.out.print("Edad: "); double edad = Double.parseDouble(sc.nextLine());
                    Mascota nueva = new Mascota(nombre, especie, edad, "Mediano", "Macho", "Mestizo", EstadoMascota.DISPONIBLE);
                    try {
                        this.actualizarCatalogo(refugio, nueva);
                    } catch (CapacidadRefugioExcedidaException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                case 2:
                    if (solicitudes.isEmpty()) {
                        System.out.println("Bandeja vacía.");
                        break;
                    }
                    SolicitudDeAdopcion s = solicitudes.get(0);
                    System.out.println("Solicitud de: " + s.getAdoptante().getNombres() + " por la mascota: " + s.getMascota().getNombre());
                    System.out.print("¿Aprobar? (si/no): ");
                    boolean aprobar = sc.nextLine().equalsIgnoreCase("si");
                    this.gestionarSolicitud(s, aprobar);
                    solicitudes.remove(s);
                    break;
                case 3:
                    System.out.println("Total global rescatadas: " + Mascota.getTotalMascotasRescatadas());
                    for (Mascota m : refugio.buscarMascota()) {
                        System.out.println("- " + m.getNombre() + " | " + m.getEspecie() + " | Estado: " + m.getEstado());
                    }
                    break;
            }
        } while (opcion != 4);
    }
}