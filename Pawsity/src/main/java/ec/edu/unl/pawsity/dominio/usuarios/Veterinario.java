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
        System.out.println("Vacuna registrada correctamente en el sistema.");
    }

    public void actualizarExpediente(Mascota mascota, String diagnostico) {
        ConsultaMedica nuevaConsulta = new ConsultaMedica(LocalDate.now(), diagnostico, this);
        mascota.getHistorialMedico().registrarConsulta(nuevaConsulta);
        System.out.println("El expediente médico de " + mascota.getNombre() + " ha sido actualizado.");
    }

    @Override
    public void redireccionarPanel(Scanner sc, Refugio refugio, List<SolicitudDeAdopcion> solicitudes) {
        int opcion = 0;
        do {
            System.out.println("\n--- Módulo Clínico Veterinario: " + this.nombres + " ---");
            System.out.println("1. Atender pacientes del refugio");
            System.out.println("2. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try { opcion = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { continue; }

            if (opcion == 1) {
                if (refugio.buscarMascota().isEmpty()) {
                    System.out.println("No hay pacientes registrados en el sistema en este momento.");
                    continue;
                }
                System.out.println("Lista de pacientes disponibles:");
                for (int i = 0; i < refugio.buscarMascota().size(); i++) {
                    System.out.println((i + 1) + ". " + refugio.buscarMascota().get(i).getNombre() + " (" + refugio.buscarMascota().get(i).getEspecie() + ")");
                }
                System.out.print("Ingrese el número del paciente a tratar: ");
                int indice = Integer.parseInt(sc.nextLine()) - 1;
                Mascota paciente = refugio.buscarMascota().get(indice);

                System.out.println("\nPaciente seleccionado: " + paciente.getNombre());
                System.out.println("A) Registrar diagnóstico | B) Aplicar vacuna | C) Ver historial médico");
                System.out.print("Seleccione la acción a realizar: ");
                String accion = sc.nextLine().toUpperCase();

                if (accion.equals("A")) {
                    System.out.print("Ingrese el diagnóstico: ");
                    String diag = sc.nextLine();
                    this.actualizarExpediente(paciente, diag);
                } else if (accion.equals("B")) {
                    System.out.print("Nombre de la vacuna suministrada: ");
                    String vac = sc.nextLine();
                    Vacuna v = new Vacuna(vac, LocalDate.now(), LocalDate.now().plusMonths(12));
                    this.registrarVacuna(paciente.getHistorialMedico(), v);
                } else if (accion.equals("C")) {
                    System.out.println("Historial médico de " + paciente.getNombre() + ":");
                    System.out.println("  - Consultas registradas: " + paciente.getHistorialMedico().consultasMedicas.size());
                    System.out.println("  - Vacunas aplicadas: " + paciente.getHistorialMedico().getVacunas().size());
                }
            }
        } while (opcion != 2);
    }
}