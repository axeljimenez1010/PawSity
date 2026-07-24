package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.ConsultaMedica;
import ec.edu.unl.pawsity.dominio.mascota.HistorialMedico;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.mascota.Vacuna;
import jakarta.persistence.*; // ⭐ IMPORTANTE: Importaciones de Jakarta EE

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "veterinarios")

@PrimaryKeyJoinColumn(name = "id_usuario")
public class Veterinario extends Usuario {

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "numero_licencia", unique = true, length = 50)
    private String numeroLicencia;


    protected Veterinario() {
        super();
    }

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
                    System.out.println("\n=== Historial Médico de " + paciente.getNombre() + " ===");

                    System.out.println("--- Consultas Registradas ---");
                    List<ConsultaMedica> consultas = paciente.getHistorialMedico().getConsultasMedicas();
                    if (consultas.isEmpty()) {
                        System.out.println("  No hay diagnósticos previos.");
                    } else {
                        for (ConsultaMedica consulta : consultas) {
                            System.out.println("  - " + consulta.detallesConsulta());
                        }
                    }

                    System.out.println("--- Vacunas Aplicadas ---");
                    List<Vacuna> vacunas = paciente.getHistorialMedico().getVacunas();
                    if (vacunas.isEmpty()) {
                        System.out.println("  No hay vacunas registradas.");
                    } else {
                        for (Vacuna vacuna : vacunas) {
                            vacuna.obtenerDetalles();
                        }
                    }
                }
            }
        } while (opcion != 2);
    }



    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }
}