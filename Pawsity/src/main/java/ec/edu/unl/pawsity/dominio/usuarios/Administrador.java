package ec.edu.unl.pawsity.dominio.usuarios;

public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    public void monitorearAdopciones() {
        System.out.println("Revisando el estado de todas las adopciones...");
    }

    public void validarInformacion() {
        System.out.println("Validando datos del sistema y del refugio.");
    }
}