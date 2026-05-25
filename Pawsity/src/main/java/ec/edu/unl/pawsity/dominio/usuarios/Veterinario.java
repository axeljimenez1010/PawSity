package ec.edu.unl.pawsity.dominio.usuarios;

public class Veterinario extends Usuario {

    public Veterinario() {
        super();
    }

    public Veterinario(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    // TODO: Reemplazar Object por ExpedienteMedico cuando esté disponible
    public void registrarVacuna(Object e) {
        System.out.println("Vacuna registrada en el expediente médico.");
    }

    public void registrarTratamiento(Object e) {
        System.out.println("Tratamiento añadido al expediente.");
    }

    public void agregarObservacion(Object e) {
        System.out.println("Observación médica anexada.");
    }
}