package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import ec.edu.unl.pawsity.servicios.RefugioService;
import ec.edu.unl.pawsity.util.MensajesUtil;
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
    private Mascota pacienteActivo;
    private String diagnostico;
    private String nombreVacuna;

    @Inject private RefugioService refugioService;
    @Inject private LoginBean loginBean;

    @PostConstruct
    public void init() {
        List<Mascota> lista = getPacientes();
        if (!lista.isEmpty()) pacienteActivo = lista.get(0); // Seleccionar el primer paciente por defecto
    }

    public void seleccionar(Mascota m) { this.pacienteActivo = m; }

    public void registrarConsulta() {
        if (pacienteActivo == null || diagnostico.trim().isEmpty()) return;
        if (!(loginBean.getUsuarioLogueado() instanceof Veterinario vet)) {
            MensajesUtil.mostrarError("Sesión inválida", "Debe iniciar sesión como veterinario.");
            return;
        }
        vet.actualizarExpediente(pacienteActivo, diagnostico);
        MensajesUtil.mostrarExito("Expediente Actualizado", "Consulta registrada para " + pacienteActivo.getNombre());
        diagnostico = "";
    }

    public void registrarVacuna() {
        if (pacienteActivo == null || nombreVacuna.trim().isEmpty()) return;
        if (!(loginBean.getUsuarioLogueado() instanceof Veterinario vet)) {
            MensajesUtil.mostrarError("Sesión inválida", "Debe iniciar sesión como veterinario.");
            return;
        }
        Vacuna vac = new Vacuna(nombreVacuna, LocalDate.now(), LocalDate.now().plusMonths(12));
        vet.registrarVacuna(pacienteActivo.getHistorialMedico(), vac);
        MensajesUtil.mostrarExito("Vacuna Aplicada", nombreVacuna + " registrada correctamente.");
        nombreVacuna = "";
    }

    public List<Mascota> getPacientes() { return refugioService.getRefugio().buscarMascota(); }
    public Mascota getPacienteActivo() { return pacienteActivo; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String d) { this.diagnostico = d; }
    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String v) { this.nombreVacuna = v; }
}