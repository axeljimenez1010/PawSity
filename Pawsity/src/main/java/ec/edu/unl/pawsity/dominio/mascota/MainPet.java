package ec.edu.unl.pawsity.dominio.mascota;

import java.util.Date;

public class MainPet {

    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String tamano; // Evitamos la 'ñ' por convenciones de Clean Code
    private String sexo;
    private String color;
    private Date fechaIngreso;

    // Relaciones basadas en el diagrama
    private StatePet estado;
    private MedicalHistory historialMedico;

    public MainPet(String nombre, String especie, String raza, Integer edad, String tamano,
                   String sexo, String color, Date fechaIngreso, StatePet estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.tamano = tamano;
        this.sexo = sexo;
        this.color = color;
        this.fechaIngreso = fechaIngreso;
        this.setEstado(estado);
    }

    public void estadoPrioridad() {
        // TODO: Implementar lógica de cálculo de prioridad
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public StatePet getEstado() { return estado; }
    public void setEstado(StatePet estado) { this.estado = estado; }

    public MedicalHistory getHistorialMedico() { return historialMedico; }
    public void setHistorialMedico(MedicalHistory historialMedico) { this.historialMedico = historialMedico; }

}
