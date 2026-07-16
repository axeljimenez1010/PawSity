package ec.edu.unl.pawsity.dominio.mascota;

import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMedica {
    public LocalDate fechaDiagnostico;
    public String diagnostico;
    public List<String> medicamentosAplicados;
    public String observaciones;
    public Veterinario veterinarioEncargado;

    public ConsultaMedica(LocalDate fechaDiagnostico, String diagnostico, Veterinario veterinarioEncargado) {
        this.fechaDiagnostico = fechaDiagnostico;
        this.diagnostico = diagnostico;
        this.veterinarioEncargado = veterinarioEncargado;
        this.medicamentosAplicados = new ArrayList<>();
    }

    public void agregarMedicamento(String medicamento) {
        this.medicamentosAplicados.add(medicamento);
    }

    public String detallesConsulta() {
        return "Fecha: " + fechaDiagnostico + " - Dr(a): " + veterinarioEncargado.getNombres() + " | Diagnóstico: " + diagnostico;
    }

    // --- Getters y Setters obligatorios para que JSF y PrimeFaces lean los datos ---

    public LocalDate getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(LocalDate fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<String> getMedicamentosAplicados() {
        return medicamentosAplicados;
    }

    public void setMedicamentosAplicados(List<String> medicamentosAplicados) {
        this.medicamentosAplicados = medicamentosAplicados;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Veterinario getVeterinarioEncargado() {
        return veterinarioEncargado;
    }

    public void setVeterinarioEncargado(Veterinario veterinarioEncargado) {
        this.veterinarioEncargado = veterinarioEncargado;
    }
}

