package ec.edu.unl.pawsity.dominio.mascota;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private LocalDate fechaCreacion;
    private List<Vacuna> vacunas;
    private List<ConsultaMedica> consultasMedicas;

    public HistorialMedico() {
        this.fechaCreacion = LocalDate.now();
        this.vacunas = new ArrayList<>();
        this.consultasMedicas = new ArrayList<>();
    }

    public void agregarVacuna(Vacuna nuevaVacuna) {
        this.vacunas.add(nuevaVacuna);
    }

    public void registrarConsulta(ConsultaMedica nuevaConsulta) {
        this.consultasMedicas.add(nuevaConsulta);
    }

    // --- GETTERS Y SETTERS ---

    public List<Vacuna> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<Vacuna> vacunas) {
        this.vacunas = vacunas;
    }

    // Este es el método que solucionará tu error de "PropertyNotFoundException"
    public List<ConsultaMedica> getConsultasMedicas() {
        return consultasMedicas;
    }

    public void setConsultasMedicas(List<ConsultaMedica> consultasMedicas) {
        this.consultasMedicas = consultasMedicas;
    }
}
