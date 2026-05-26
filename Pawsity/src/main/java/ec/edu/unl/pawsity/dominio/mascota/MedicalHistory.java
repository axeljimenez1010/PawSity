package ec.edu.unl.pawsity.dominio.mascota;

import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistory {

    private LocalDate fechaCreacion;
    private String ultimaVacuna;
    private String tratamientoActual;
    private String observacion;
    private List<Veterinario> veterinariosAsignados;

    public MedicalHistory() {
        // La fecha de creacion se asigna automaticamente al momento de instanciar
        this.fechaCreacion = LocalDate.now();
        // Inicializamos la lista para evitar NullPointerExceptions
        this.veterinariosAsignados = new ArrayList<>();
        this.ultimaVacuna = "Sin registro";
        this.tratamientoActual = "Ninguno";
        this.observacion = "Ingreso inicial";
    }

    // Metodos de comportamiento
    public void actualizarHistorial() {
        // TODO: Implementar lógica de persistencia o actualización de datos
    }

    public void agregarVeterinario(Veterinario veterinario) {
        if (veterinario != null && !this.veterinariosAsignados.contains(veterinario)) {
            this.veterinariosAsignados.add(veterinario);
        }
    }

    // Getters y Setters
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public String getUltimaVacuna() { return ultimaVacuna; }
    public void setUltimaVacuna(String ultimaVacuna) { this.ultimaVacuna = ultimaVacuna; }

    public String getTratamientoActual() { return tratamientoActual; }
    public void setTratamientoActual(String tratamientoActual) { this.tratamientoActual = tratamientoActual; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public List<Veterinario> getVeterinariosAsignados() { return veterinariosAsignados; }
}
