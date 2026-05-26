package ec.edu.unl.pawsity.dominio.mascota;

import java.time.LocalDate;

public class Pet {

    private String nombre;
    private String especie;
    private String raza;
    private double edad; //
    private String tamano;
    private String sexo;
    private String color;
    private LocalDate fechaIngreso;
    private StatePet estado;

    // Composicion: La mascota posee existencialmente su historial medico
    private MedicalHistory historialMedico;

    // Constructor principal
    public Pet(String nombre, String especie, String raza, double edad,
               String tamano, String sexo, String color, StatePet estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.tamano = tamano;
        this.sexo = sexo;
        this.color = color;
        this.estado = estado;

        // Asignacion automatica de la fecha actual de ingreso
        this.fechaIngreso = LocalDate.now();

        // Cumplimiento de la regla de Composicion del diagrama UML
        this.historialMedico = new MedicalHistory();
    }

    // Metodos de negocio
    public void estadoPrioridad() {
        // TODO: Implementar lógica de cálculo de prioridad basada en fechaIngreso
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public double getEdad() { return edad; }
    public void setEdad(double edad) { this.edad = edad; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    // No creamos setFechaIngreso() para proteger la inmutabilidad del dato de creación

    public StatePet getEstado() { return estado; }
    public void setEstado(StatePet estado) { this.estado = estado; }

    public MedicalHistory getHistorialMedico() { return historialMedico; }


    // No creamos setHistorialMedico() para evitar que alguien sobreescriba el historial original

}
