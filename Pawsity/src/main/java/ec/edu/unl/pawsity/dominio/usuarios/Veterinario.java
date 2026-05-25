package ec.edu.unl.pawsity.dominio.usuarios;

public class Veterinario extends Usuario {

    public Veterinario() {
        super();
    }

    public Veterinario(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    // TODO: Reemplazar Object por HistorialMedico cuando se integre el código
    public void registrarVacuna(Object h) {
        System.out.println("Vacuna registrada en el historial médico.");
    }

    // TODO: Reemplazar Object por HistorialMedico
    public void registrarTratamiento(Object h) {
        System.out.println("Tratamiento añadido al historial.");
    }

    // TODO: Reemplazar Object por HistorialMedico
    public void registrarObservaciones(Object h) {
        System.out.println("Observaciones médicas anexadas.");
    }
}