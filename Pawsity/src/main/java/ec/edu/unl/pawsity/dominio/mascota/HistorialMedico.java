package ec.edu.unl.pawsity.dominio.mascota;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedico {
    private LocalDate fechaCreacion;
    private List<Vacuna> vacunas;
    public List<ConsultaMedica> consultasMedicas;

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

    public List<Vacuna> getVacunas() {
        return vacunas;
    }
}
