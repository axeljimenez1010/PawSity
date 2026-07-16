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
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public double getEdad() { return edad; }
    public void setEdad(double edad) { this.edad = edad; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public EstadoMascota getEstado() { return estado; }
    public void setEstado(EstadoMascota estado) { this.estado = estado; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public HistorialMedico getHistorialMedico() { return historialMedico; }
    public void setHistorialMedico(HistorialMedico historialMedico) { this.historialMedico = historialMedico; }
}


