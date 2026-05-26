package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.mascota.MedicalHistory;

public class Veterinario extends Usuario {

    public Veterinario() {
        super();
    }

    public Veterinario(String correoElectronico, String contrasena, String nombres, String apellidos) {
        super(correoElectronico, contrasena, nombres, apellidos);
    }

    public void registrarVacuna(MedicalHistory historial, String ultimaVacuna) {
        historial.setUltimaVacuna(ultimaVacuna);
        System.out.println("Vacuna registrada: " + ultimaVacuna);
    }

    public void registrarTratamiento(MedicalHistory historial, String tratamientoActual) {
        historial.setTratamientoActual(tratamientoActual);
        System.out.println("Tratamiento registrado: " + tratamientoActual);
    }

    public void registrarObservaciones(MedicalHistory historial, String observacion) {
        historial.setObservacion(observacion);
        System.out.println("Observación registrada: " + observacion);
    }
}