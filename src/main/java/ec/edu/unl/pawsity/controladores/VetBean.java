package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import ec.edu.unl.pawsity.repositorios.MascotaRepository;
import ec.edu.unl.pawsity.util.FacesUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named("vetBean")
@ViewScoped
public class VetBean implements Serializable {

    @Inject private MascotaRepository mascotaRepository;
    @Inject private UsuarioSession usuarioSession;

    private List<Mascota> pacientes;
    private Mascota pacienteActivo;
    private String diagnostico;
    private String nombreVacuna;

    @PostConstruct
    public void init() {
        if (usuarioSession == null || !usuarioSession.isVeterinario()) {
            FacesUtil.addError("Acceso Restringido", "No tienes permisos de veterinario para acceder a este módulo.");
            return;
        }

        // --- MEJORA 1: Cargamos únicamente los pacientes que siguen residiendo en el refugio ---
        cargarPacientes();

        if (this.pacientes != null && !this.pacientes.isEmpty()) {
            this.pacienteActivo = this.pacientes.get(0); // Seleccionar el primer paciente por defecto
        }
    }

    /**
     * Método auxiliar para consultar en PostgreSQL evadiendo la caché
     * y excluyendo a las mascotas que ya fueron adoptadas.
     */
    public void cargarPacientes() {
        this.pacientes = mascotaRepository.buscarActivasParaVeterinario();
    }

    public void seleccionar(Mascota m) {
        this.pacienteActivo = m;
    }

    /**
     * Registra una nueva consulta médica / diagnóstico y lo persiste en base de datos.
     */
    public void registrarConsulta() {
        if (pacienteActivo == null || diagnostico == null || diagnostico.trim().isEmpty()) {
            FacesUtil.addWarn("Atención", "Debe seleccionar un paciente y redactar un diagnóstico.");
            return;
        }

        if (usuarioSession == null || !usuarioSession.isVeterinario()) {
            FacesUtil.addError("Sesión inválida", "Debe iniciar sesión como veterinario.");
            return;
        }

        try {
            Veterinario vetActual = (Veterinario) usuarioSession.getUsuarioActual();

            // Usamos el método de negocio de la entidad que asocia el diagnóstico al veterinario
            vetActual.actualizarExpediente(pacienteActivo, diagnostico.trim());

            // Persistimos los cambios del historial médico en PostgreSQL vía JPA
            mascotaRepository.actualizar(pacienteActivo);

            // Refrescamos la memoria del panel
            cargarPacientes();

            FacesUtil.addInfo("Expediente Actualizado", "Consulta registrada correctamente para " + pacienteActivo.getNombre());
            this.diagnostico = "";

        } catch (Exception e) {
            FacesUtil.addError("Error Clínico", "No se pudo guardar la consulta en la base de datos: " + e.getMessage());
        }
    }

    /**
     * Aplica y registra una vacuna en el expediente clínico del animal activo.
     */
    public void registrarVacuna() {
        if (pacienteActivo == null || nombreVacuna == null || nombreVacuna.trim().isEmpty()) {
            FacesUtil.addWarn("Atención", "Debe seleccionar un paciente e ingresar el nombre de la vacuna.");
            return;
        }

        if (usuarioSession == null || !usuarioSession.isVeterinario()) {
            FacesUtil.addError("Sesión inválida", "Debe iniciar sesión como veterinario.");
            return;
        }

        try {
            Veterinario vetActual = (Veterinario) usuarioSession.getUsuarioActual();

            Vacuna vac = new Vacuna(nombreVacuna.trim(), LocalDate.now(), LocalDate.now().plusMonths(12));

            vetActual.registrarVacuna(pacienteActivo.getHistorialMedico(), vac);

            // Persistimos en base de datos
            mascotaRepository.actualizar(pacienteActivo);

            // Refrescamos la memoria del panel
            cargarPacientes();

            FacesUtil.addInfo("Vacuna Aplicada", nombreVacuna + " registrada correctamente en el expediente.");
            this.nombreVacuna = "";

        } catch (Exception e) {
            FacesUtil.addError("Error Clínico", "No se pudo registrar la vacuna: " + e.getMessage());
        }
    }

    // --- MEJORA 2: GETTER OPTIMIZADO PARA EVITAR COLAPSO DE BD EN PRIMEFACES ---
    public List<Mascota> getPacientes() {
        return this.pacientes;
    }
    public void setPacientes(List<Mascota> pacientes) { this.pacientes = pacientes; }

    public Mascota getPacienteActivo() { return pacienteActivo; }
    public void setPacienteActivo(Mascota pacienteActivo) { this.pacienteActivo = pacienteActivo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String nombreVacuna) { this.nombreVacuna = nombreVacuna; }
}