package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.Administrador;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;
import ec.edu.unl.pawsity.servicios.RefugioService;
import ec.edu.unl.pawsity.util.MensajesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("adminBean")
@ViewScoped
public class AdminBean implements Serializable {
    private String nombre;
    private String especie;
    private double edad;
    private String tamano;
    private String color;
    private String sexo;

    @Inject private RefugioService refugioService;
    @Inject private LoginBean loginBean;

    public void registrarMascota() {
        try {
            Administrador admin = (Administrador) loginBean.getUsuarioLogueado();
            Mascota nueva = new Mascota(nombre, especie, edad, tamano, sexo, color, EstadoMascota.DISPONIBLE);
            admin.actualizarCatalogo(refugioService.getRefugio(), nueva);
            MensajesUtil.mostrarExito("¡Registro Exitoso!", nombre + " ha sido ingresado al catálogo.");
            limpiar();
        } catch (CapacidadRefugioExcedidaException e) {
            MensajesUtil.mostrarError("Límite Excedido", e.getMessage());
        }
    }

    public void gestionar(SolicitudDeAdopcion sol, boolean aprobar) {
        Administrador admin = (Administrador) loginBean.getUsuarioLogueado();
        admin.gestionarSolicitud(sol, aprobar);
        refugioService.getSistemaDeSolicitudes().remove(sol);
        if (aprobar) MensajesUtil.mostrarExito("Solicitud Aprobada", "La mascota ha sido adoptada.");
        else MensajesUtil.mostrarAdvertencia("Solicitud Rechazada", "La mascota vuelve a estar disponible.");
    }

    private void limpiar() { nombre=""; especie=""; edad=0; tamano=""; color=""; sexo=""; }
    public List<SolicitudDeAdopcion> getSolicitudes() { return refugioService.getSistemaDeSolicitudes(); }
    // Getters y Setters de los inputs (nombre, especie, etc.) obligatorios aquí...
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public double getEdad() { return edad; }
    public void setEdad(double edad) { this.edad = edad; }
    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}