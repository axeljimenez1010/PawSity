package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.mascota.HistorialMedico;
import ec.edu.unl.pawsity.dominio.mascota.Vacuna;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;

public class Veterinario extends Usuario {
    private String especialidad;
    private String numeroLicencia;

    public Veterinario(String correo, String contrasena, String nombres, String apellidos, String especialidad, String numeroLicencia) {
        super(correo, contrasena, nombres, apellidos);
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
    }

    public void registrarVacuna(HistorialMedico historial, Vacuna nuevaVacuna) {
        historial.agregarVacuna(nuevaVacuna);
    }

    public void actualizarExpediente(Mascota mascota, String diagnostico) {
        System.out.println("Expediente médico actualizado para: " + mascota.getNombre());
    }

    @Override
    public void redireccionarPanel() {
        System.out.println(">> Abriendo Sistema Clínico de VETERINARIO...");
    }
}