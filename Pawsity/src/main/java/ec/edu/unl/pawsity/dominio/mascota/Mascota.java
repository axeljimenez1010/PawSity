package ec.edu.unl.pawsity.dominio.mascota;
import java.time.LocalDate;

public class Mascota {
    private static int totalMascotasRescatadas = 0;

    private String nombre;
    private String especie;
    private double edad;
    private String tamano;
    private String sexo;
    private String color;
    private EstadoMascota estado;
    private LocalDate fechaIngreso;
    private HistorialMedico historialMedico;

    public Mascota(String nombre, String especie, double edad, String tamano, String sexo, String color, EstadoMascota estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.tamano = tamano;
        this.sexo = sexo;
        this.color = color;
        this.estado = estado;
        this.fechaIngreso = LocalDate.now();
        this.historialMedico = new HistorialMedico();
        totalMascotasRescatadas++;
    }

    public static int getTotalMascotasRescatadas() {
        return totalMascotasRescatadas;
    }

    public void estadoPrioridad() {
        System.out.println("Calculando prioridad de adopción para: " + nombre);
    }

    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public EstadoMascota getEstado() { return estado; }
    public void setEstado(EstadoMascota estado) { this.estado = estado; }
    public HistorialMedico getHistorialMedico() { return historialMedico; }
}
