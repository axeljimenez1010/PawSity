package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.ConsultaMedica;
import ec.edu.unl.pawsity.dominio.mascota.HistorialMedico;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.mascota.Vacuna;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

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
        System.out.println(">> Vacuna registrada en la cartilla.");
    }

    public void actualizarExpediente(Mascota mascota, String diagnostico) {
        ConsultaMedica nuevaConsulta = new ConsultaMedica(LocalDate.now(), diagnostico, this);
        mascota.getHistorialMedico().registrarConsulta(nuevaConsulta);
        System.out.println(">> Expediente médico actualizado para: " + mascota.getNombre());
    }

    @Override
    public void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes) {
        int opcion = 0;
        do {
            System.out.println("\n=== CLÍNICA VETERINARIA: Dr/Dra. " + this.nombres + " ===");
            System.out.println("1. Atender Pacientes");
            System.out.println("2. Cerrar Sesión");
            System.out.print("Seleccione: ");

            try { opcion = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { continue; }

            if (opcion == 1) {
                if (refugio.buscarMascota().isEmpty()) {
                    System.out.println("No hay pacientes en el refugio.");
                    continue;
                }
                for (int i = 0; i < refugio.buscarMascota().size(); i++) {
                    System.out.println((i + 1) + ". " + refugio.buscarMascota().get(i).getNombre() + " (" + refugio.buscarMascota().get(i).getEspecie() + ")");
                }
                System.out.print("Seleccione el paciente a tratar (número): ");
                int indice = Integer.parseInt(sc.nextLine()) - 1;
                Mascota paciente = refugio.buscarMascota().get(indice);

                System.out.println("\nAtendiendo a: " + paciente.getNombre());
                System.out.println("A) Registrar Diagnóstico | B) Aplicar Vacuna | C) Ver Historial");
                System.out.print("Acción: ");
                String accion = sc.nextLine().toUpperCase();

                if (accion.equals("A")) {
                    System.out.print("Escriba el diagnóstico: ");
                    String diag = sc.nextLine();
                    this.actualizarExpediente(paciente, diag);
                } else if (accion.equals("B")) {
                    System.out.print("Nombre de la vacuna: ");
                    String vac = sc.nextLine();
                    Vacuna v = new Vacuna(vac, LocalDate.now(), LocalDate.now().plusMonths(12));
                    this.registrarVacuna(paciente.getHistorialMedico(), v);
                } else if (accion.equals("C")) {
                    System.out.println("Consultas Previas: " + paciente.getHistorialMedico().consultasMedicas.size());
                    System.out.println("Vacunas Aplicadas: " + paciente.getHistorialMedico().getVacunas().size());
                }
            }
        } while (opcion != 2);
    }
}