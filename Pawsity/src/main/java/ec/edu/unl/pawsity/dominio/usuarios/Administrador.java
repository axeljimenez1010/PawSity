package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;

public class Administrador extends Usuario {
    private String puesto;

    public Administrador(String correo, String contrasena, String nombres, String apellidos, String puesto) {
        super(correo, contrasena, nombres, apellidos);
        this.puesto = puesto;
    }

    public void gestionarSolicitud(SolicitudDeAdopcion solicitud, boolean estadoAprobacion) {
        System.out.println("Gestionando solicitud de adopción...");
    }

    public void actualizarCatalogo(Refugio refugio, Mascota mascota) {
        System.out.println("Catálogo actualizado.");
    }

    @Override
    public void redireccionarPanel() {
        System.out.println(">> Abriendo Panel de Control de ADMINISTRADOR...");
    }
}