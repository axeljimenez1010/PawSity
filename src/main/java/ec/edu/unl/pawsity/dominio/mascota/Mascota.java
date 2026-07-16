package ec.edu.unl.pawsity.dominio.mascota;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    private String imagenUrl;

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
        this.imagenUrl = generarImagenPorDefecto(especie, nombre);
        totalMascotasRescatadas++;
    }

    public static int getTotalMascotasRescatadas() {
        return totalMascotasRescatadas;
    }

    public void estadoPrioridad() {
        System.out.println("Calculando prioridad de adopción para: " + nombre);
    }

    private static String generarImagenPorDefecto(String especie, String nombre) {
        String categoria = mapearCategoria(especie);
        int lock = Math.abs((nombre == null ? "mascota" : nombre).hashCode() % 1000);
        return "https://loremflickr.com/480/360/" + categoria + "?lock=" + lock;
    }

    private static String mapearCategoria(String especie) {
        if (especie == null) return "pet";
        String e = especie.trim().toLowerCase();
        if (e.startsWith("can")) return "dog";
        if (e.startsWith("fel") || e.startsWith("gat")) return "cat";
        if (e.startsWith("ave") || e.startsWith("bird")) return "bird";
        if (e.startsWith("cone") || e.startsWith("rabbit")) return "rabbit";
        if (e.startsWith("roed") || e.startsWith("hamster")) return "hamster";
        if (e.startsWith("rept") || e.startsWith("lizard")) return "lizard";
        return "pet";
    }

    public String getFechaIngresoFormateada() {
        return fechaIngreso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public long getDiasEnRefugio() {
        return ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now());
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

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}