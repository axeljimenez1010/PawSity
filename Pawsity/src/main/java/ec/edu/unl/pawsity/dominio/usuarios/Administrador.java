package ec.edu.unl.pawsity.dominio.usuarios;

public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    public void validarInformacion() {
        System.out.println("Validando datos registrados en el refugio...");
    }

    public void monitorearAdopciones() {
        System.out.println("Supervisando el estado de las adopciones.");
    }
}