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
}
