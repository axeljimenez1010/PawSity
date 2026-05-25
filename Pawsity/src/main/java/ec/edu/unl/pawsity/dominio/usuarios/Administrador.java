package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;

public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    public void validarInformacion(Refugio refugio) {
        System.out.println("Validando datos registrados en el refugio...");
    }

    public void monitorearAdopciones(Refugio refugio) {
        System.out.println("Supervisando el estado de las adopciones.");
    }
}