package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void enviarSolicitud(Mascota mascotaDeseada, List<SolicitudDeAdopcion> bandejaGlobal) {
        System.out.println(">> Solicitud enviada para adoptar a: " + mascotaDeseada.getNombre());
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
            System.out.println("\n=== PORTAL DE ADOPTANTE: " + this.nombres + " ===");
            System.out.println("1. Explorar todas las mascotas disponibles");
            System.out.println("2. Buscar mascota por especie");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Seleccione: ");

            try { opcion = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { continue; }

            if (opcion == 1 || opcion == 2) {
                List<Mascota> catalogo = new ArrayList<>();

                if(opcion == 1) {
                    for(Mascota m : refugio.buscarMascota()) {
                        if(m.getEstado() == EstadoMascota.DISPONIBLE) catalogo.add(m);
                    }
                } else {
                    System.out.print("Ingrese la especie (Ej. Canino, Felino): ");
                    catalogo = this.buscarMascota(sc.nextLine(), refugio);
                }

                if(catalogo.isEmpty()) {
                    System.out.println("No se encontraron mascotas disponibles con ese criterio.");
                    continue;
                }

                for (int i = 0; i < catalogo.size(); i++) {
                    System.out.println((i + 1) + ". " + catalogo.get(i).getNombre() + " (" + catalogo.get(i).getEspecie() + ")");
                }

                System.out.print("\n¿Desea solicitar la adopción de alguna? Ingrese el número (0 para cancelar): ");
                int seleccion = Integer.parseInt(sc.nextLine());
                if (seleccion > 0 && seleccion <= catalogo.size()) {
                    Mascota elegida = catalogo.get(seleccion - 1);
                    this.enviarSolicitud(elegida, solicitudes);
                    System.out.println("⏳ Su solicitud está ahora en espera de aprobación por un Administrador.");
                }
            }
        } while (opcion != 3);
    }
}
