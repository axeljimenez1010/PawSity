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
        // Usamos el hashcode del nombre para seleccionar consistentemente una de las 3 fotos
        int lock = Math.abs((nombre == null ? "mascota" : nombre).hashCode() % 3);

        switch (categoria) {
            case "dog":
                String[] perros = {
                        "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?q=80&w=600&auto=format&fit=crop", // Cachorro canela (Oliver)
                        "https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=600&auto=format&fit=crop", // Perro feliz (Luna)
                        "https://images.unsplash.com/photo-1552053831-71594a27632d?q=80&w=600&auto=format&fit=crop"  // Golden retriever
                };
                return perros[lock];

            case "cat":
                String[] gatos = {
                        "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?q=80&w=600&auto=format&fit=crop", // Gato elegante (Max)
                        "https://images.unsplash.com/photo-1573865526739-10659fec78a5?q=80&w=600&auto=format&fit=crop", // Gato curioso
                        "https://images.unsplash.com/photo-1495360010541-f48722b34f7d?q=80&w=600&auto=format&fit=crop"  // Gato durmiendo
                };
                return gatos[lock];

            default:
                // Imagen neutra y bonita de refugio para conejos, aves, reptiles, etc.
                return "https://images.unsplash.com/photo-1450778869180-41d0601e046e?q=80&w=600&auto=format&fit=crop";
        }
    }

    private static String mapearCategoria(String especie) {
        if (especie == null) return "pet";
        String e = especie.trim().toLowerCase();
        if (e.startsWith("can")) return "dog";
        if (e.startsWith("fel") || e.startsWith("gat")) return "cat";
        return "pet";
    }

    public String getFechaIngresoFormateada() {
        return fechaIngreso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public long getDiasEnRefugio() {
        return ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now());
    }

    // --- Getters y Setters ---

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