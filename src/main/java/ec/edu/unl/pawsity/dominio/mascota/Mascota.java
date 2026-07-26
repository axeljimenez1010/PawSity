package ec.edu.unl.pawsity.dominio.mascota;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "mascotas")
@NamedQueries({
        @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
        @NamedQuery(name = "Mascota.findByEstado", query = "SELECT m FROM Mascota m WHERE m.estado = :estado"),
        @NamedQuery(name = "Mascota.findByEspecie", query = "SELECT m FROM Mascota m WHERE LOWER(m.especie) = LOWER(:especie) AND m.estado = :estado")
})
public class Mascota implements Serializable {

    // Los atributos estáticos no son persistidos por JPA (equivalente implícito a @Transient)
    private static int totalMascotasRescatadas = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long id;

    // Validaciones a nivel de vista/modelo (Jakarta Validation) y Base de Datos (JPA)
    @NotNull
    @NotEmpty
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @NotNull
    @NotEmpty
    @Column(name = "especie", nullable = false, length = 50)
    private String especie;

    @Column(name = "edad", nullable = false)
    private double edad;

    @NotNull
    @NotEmpty
    @Column(name = "tamano", nullable = false, length = 30)
    private String tamano;

    @NotNull
    @NotEmpty
    @Column(name = "sexo", nullable = false, length = 20)
    private String sexo;

    @NotNull
    @NotEmpty
    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoMascota estado;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_historial_medico", referencedColumnName = "id_historial", unique = true)
    private HistorialMedico historialMedico;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    public Mascota() {
        // Constructor vacío requerido por el motor JPA
    }

    public Mascota(Long id, @NotNull @NotEmpty String nombre, @NotNull @NotEmpty String especie, double edad,
                   @NotNull @NotEmpty String tamano, @NotNull @NotEmpty String sexo, @NotNull @NotEmpty String color,
                   @NotNull EstadoMascota estado) {
        this();
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la mascota es requerido");
        this.especie = Objects.requireNonNull(especie, "La especie es requerida");
        this.edad = edad;
        this.tamano = Objects.requireNonNull(tamano, "El tamaño es requerido");
        this.sexo = Objects.requireNonNull(sexo, "El sexo es requerido");
        this.color = Objects.requireNonNull(color, "El color es requerido");
        this.estado = Objects.requireNonNull(estado, "El estado de adopción es requerido");
        this.fechaIngreso = LocalDate.now();
        this.historialMedico = new HistorialMedico();
        this.imagenUrl = generarImagenPorDefecto(especie, nombre);
        totalMascotasRescatadas++;
    }

    public Mascota(String nombre, String especie, double edad, String tamano, String sexo, String color, EstadoMascota estado) {
        this(null, nombre, especie, edad, tamano, sexo, color, estado);
    }

    public static int getTotalMascotasRescatadas() {
        return totalMascotasRescatadas;
    }

    public void estadoPrioridad() {
        System.out.println("Calculando prioridad de adopción para: " + nombre);
    }

    private static String generarImagenPorDefecto(String especie, String nombre) {
        String categoria = mapearCategoria(especie);
        int lock = Math.abs((nombre == null ? "mascota" : nombre).hashCode() % 3);

        switch (categoria) {
            case "dog":
                String[] perros = {
                        "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?q=80&w=600&auto=format&fit=crop",
                        "https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=600&auto=format&fit=crop",
                        "https://images.unsplash.com/photo-1552053831-71594a27632d?q=80&w=600&auto=format&fit=crop"
                };
                return perros[lock];

            case "cat":
                String[] gatos = {
                        "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?q=80&w=600&auto=format&fit=crop",
                        "https://images.unsplash.com/photo-1573865526739-10659fec78a5?q=80&w=600&auto=format&fit=crop",
                        "https://images.unsplash.com/photo-1495360010541-f48722b34f7d?q=80&w=600&auto=format&fit=crop"
                };
                return gatos[lock];

            default:
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
        return fechaIngreso != null ? fechaIngreso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public long getDiasEnRefugio() {
        return fechaIngreso != null ? ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now()) : 0;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    /**
     * Getter blindado con Inicialización Perezosa (Lazy Initialization).
     * Si por alguna razón el historial médico viene nulo de la base de datos,
     * lo instancia automáticamente para evitar NullPointerException en el Módulo Veterinario.
     */
    public HistorialMedico getHistorialMedico() {
        if (this.historialMedico == null) {
            this.historialMedico = new HistorialMedico();
        }
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) { this.historialMedico = historialMedico; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getFechaIngreso());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(getId(), mascota.getId()) &&
                Objects.equals(getNombre(), mascota.getNombre()) &&
                Objects.equals(getFechaIngreso(), mascota.getFechaIngreso());
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", edad=" + edad +
                ", estado=" + estado +
                '}';
    }
}