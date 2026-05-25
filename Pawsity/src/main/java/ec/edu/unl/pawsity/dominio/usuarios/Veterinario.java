package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.mascota.MedicalHistory;

public class Veterinario extends Usuario {

    public Veterinario() {
        super();
    }

    public Veterinario(String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena);
    }

    public void registrarVacuna(MedicalHistory h) {
        System.out.println("Vacuna registrada en el historial médico.");
    }

    public void registrarTratamiento(MedicalHistory h) {
        System.out.println("Tratamiento añadido al historial.");
    }

    public void registrarObservaciones(MedicalHistory h) {
        System.out.println("Observaciones médicas anexadas.");
    }
}