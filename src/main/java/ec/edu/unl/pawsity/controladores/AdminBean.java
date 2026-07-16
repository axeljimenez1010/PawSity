package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.Administrador;
import ec.edu.unl.pawsity.excepciones.CapacidadRefugioExcedidaException;
import ec.edu.unl.pawsity.servicios.RefugioService;
import ec.edu.unl.pawsity.util.MensajesUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part; // Importante para recibir el archivo subido

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Named("adminBean")
@ViewScoped
public class AdminBean implements Serializable {
    private String nombre;
    private String especie;
    private double edad;
    private String tamano;
    private String color;
    private String sexo;
    private String imagenUrl;

    // 1. Nuevo atributo para manejar la foto subida desde la PC
    private Part fotoSubida;

    // 2. Lista para almacenar las especies disponibles
    private List<String> especiesDisponibles;

    @Inject private RefugioService refugioService;
    @Inject private LoginBean loginBean;

    @PostConstruct
    public void init() {
        // Inicializamos las opciones del menú desplegable
        especiesDisponibles = Arrays.asList("Canino", "Felino", "Ave", "Conejo", "Roedor", "Reptil", "Otro");
    }

    public void registrarMascota() {
        if (!(loginBean.getUsuarioLogueado() instanceof Administrador admin)) {
            MensajesUtil.mostrarError("Sesión inválida", "Debe iniciar sesión como administrador.");
            return;
        }
        try {
            Mascota nueva = new Mascota(nombre, especie, edad, tamano, sexo, color, EstadoMascota.DISPONIBLE);

            // 3. Lógica para procesar y guardar la imagen subida
            if (fotoSubida != null && fotoSubida.getSize() > 0) {
                try {
                    // Generar un nombre único (UUID) para evitar sobreescribir imágenes con el mismo nombre
                    String nombreArchivo = UUID.randomUUID().toString() + "_" + fotoSubida.getSubmittedFileName();

                    // Obtener la ruta real de tu proyecto web para guardar la imagen
                    String rutaDirectorio = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagenes/");
                    File directorio = new File(rutaDirectorio);

                    // Si la carpeta "imagenes" no existe, la creamos
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }

                    // Copiar el archivo subido a la carpeta destino
                    File archivoDestino = new File(directorio, nombreArchivo);
                    try (InputStream input = fotoSubida.getInputStream()) {
                        Files.copy(input, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Establecer la ruta relativa para que el frontend pueda cargarla
                    nueva.setImagenUrl("resources/imagenes/" + nombreArchivo);

                } catch (Exception e) {
                    MensajesUtil.mostrarError("Error", "No se pudo guardar la imagen en el servidor.");
                    return;
                }
            } else if (imagenUrl != null && !imagenUrl.isBlank()) {
                // Mantiene tu opción original de insertar una URL de internet si no suben archivo
                nueva.setImagenUrl(imagenUrl.trim());
            }

            admin.actualizarCatalogo(refugioService.getRefugio(), nueva);
            MensajesUtil.mostrarExito("¡Registro Exitoso!", nombre + " ha sido ingresado al catálogo.");
            limpiar();
        } catch (CapacidadRefugioExcedidaException e) {
            MensajesUtil.mostrarError("Límite Excedido", e.getMessage());
        }
    }

    public void gestionar(SolicitudDeAdopcion sol, boolean aprobar) {
        if (!(loginBean.getUsuarioLogueado() instanceof Administrador admin)) {
            MensajesUtil.mostrarError("Sesión inválida", "Debe iniciar sesión como administrador.");
            return;
        }
        admin.gestionarSolicitud(sol, aprobar);
        refugioService.getSistemaDeSolicitudes().remove(sol);
        if (aprobar) MensajesUtil.mostrarExito("Solicitud Aprobada", "La mascota ha sido adoptada.");
        else MensajesUtil.mostrarAdvertencia("Solicitud Rechazada", "La mascota vuelve a estar disponible.");
    }

    private void limpiar() {
        nombre=""; especie=""; edad=0; tamano=""; color=""; sexo=""; imagenUrl="";
        fotoSubida = null; // Limpiar la foto del caché
    }

    public List<SolicitudDeAdopcion> getSolicitudes() { return refugioService.getSistemaDeSolicitudes(); }

    // --- GETTERS Y SETTERS ---
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
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    // Getters y Setters de los nuevos atributos
    public Part getFotoSubida() { return fotoSubida; }
    public void setFotoSubida(Part fotoSubida) { this.fotoSubida = fotoSubida; }
    public List<String> getEspeciesDisponibles() { return especiesDisponibles; }
    public void setEspeciesDisponibles(List<String> especiesDisponibles) { this.especiesDisponibles = especiesDisponibles; }
}