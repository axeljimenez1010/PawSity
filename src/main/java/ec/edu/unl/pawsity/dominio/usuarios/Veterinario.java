package ec.edu.unl.pawsity.dominio.usuarios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.Refugio;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.ConsultaMedica;
import ec.edu.unl.pawsity.dominio.mascota.HistorialMedico;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.mascota.Vacuna;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "veterinarios")
@PrimaryKeyJoinColumn(name = "id_usuario")
@NamedQueries({
        @NamedQuery(name = "Veterinario.findByNumeroLicencia", query = "SELECT v FROM Veterinario v WHERE v.numeroLicencia = :licencia"),
        @NamedQuery(name = "Veterinario.findByEspecialidad", query = "SELECT v FROM Veterinario v WHERE LOWER(v.especialidad) = LOWER(:especialidad)")
})
public class Veterinario extends Usuario implements Serializable {

    @NotNull
    @NotEmpty
    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @NotNull
    @NotEmpty
    @Column(name = "numero_licencia", unique = true, nullable = false, length = 50)
    private String numeroLicencia;

    public Veterinario() {
        super();
    }

    public Veterinario(Long id, @NotNull @NotEmpty String correo, @NotNull @NotEmpty String contrasena,
                       @NotNull @NotEmpty String nombres, @NotNull @NotEmpty String apellidos,
                       @NotNull @NotEmpty String especialidad, @NotNull @NotEmpty String numeroLicencia) {
        super(id, correo, contrasena, nombres, apellidos);
        this.especialidad = Objects.requireNonNull(especialidad, "La especialidad es requerida");
        this.numeroLicencia = Objects.requireNonNull(numeroLicencia, "El número de licencia es requerido");
    }

    public Veterinario(String correo, String contrasena, String nombres, String apellidos,
                       String especialidad, String numeroLicencia) {
        this(0L, correo, contrasena, nombres, apellidos, especialidad, numeroLicencia);
    }

    public void registrarVacuna(HistorialMedico historial, Vacuna nuevaVacuna) {
        Objects.requireNonNull(historial, "El historial médico es requerido");
        Objects.requireNonNull(nuevaVacuna, "La vacuna a registrar es requerida");

        historial.agregarVacuna(nuevaVacuna);
        System.out.println("Vacuna registrada correctamente en el sistema.");
    }

    public void actualizarExpediente(Mascota mascota, String diagnostico) {
        Objects.requireNonNull(mascota, "La mascota es requerida");
        Objects.requireNonNull(diagnostico, "El diagnóstico es requerido");

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

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            if (opcion == 1) {
                List<Mascota> pacientes = refugio.buscarMascota();
                if (pacientes.isEmpty()) {
                    System.out.println("No hay pacientes registrados en el sistema en este momento.");
                    continue;
                }
                System.out.println("\nLista de pacientes disponibles:");
                for (int i = 0; i < pacientes.size(); i++) {
                    System.out.println((i + 1) + ". " + pacientes.get(i).getNombre() + " (" + pacientes.get(i).getEspecie() + ")");
                }

                System.out.print("Ingrese el número del paciente a tratar: ");
                int seleccion;
                try {
                    seleccion = Integer.parseInt(sc.nextLine());
                    if (seleccion < 1 || seleccion > pacientes.size()) {
                        System.out.println("Número de paciente fuera de rango. Operación cancelada.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida. Por favor ingrese un valor numérico.");
                    continue;
                }

                Mascota paciente = pacientes.get(seleccion - 1);

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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumeroLicencia());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Veterinario that = (Veterinario) o;
        return Objects.equals(getNumeroLicencia(), that.getNumeroLicencia());
    }

    @Override
    public String toString() {
        return "Veterinario{" +
                "id=" + getId() +
                ", correoElectronico='" + getCorreoElectronico() + '\'' +
                ", nombres='" + getNombres() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", numeroLicencia='" + numeroLicencia + '\'' +
                '}';
    }
}