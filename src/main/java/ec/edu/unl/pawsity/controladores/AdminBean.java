package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.repositorios.MascotaRepository;
import ec.edu.unl.pawsity.repositorios.SolicitudRepository;
import ec.edu.unl.pawsity.util.FacesUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;

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

    // --- INYECCIÓN DE DEPENDENCIAS (JPA & Seguridad) ---
    @Inject private MascotaRepository mascotaRepository;
    @Inject private SolicitudRepository solicitudRepository;
    @Inject private UsuarioSession usuarioSession;

    // --- VARIABLES DE VISTA (Tablas y Listados en PrimeFaces) ---
    private List<Mascota> censoMascotas;
    private List<SolicitudDeAdopcion> solicitudesPendientes;
    private List<SolicitudDeAdopcion> solicitudes; // Sincronizado para compatibilidad con XHTML
    private List<String> especiesDisponibles;

    // --- VARIABLES DE FORMULARIO (Registrar Nueva Mascota) ---
    private String nombre;
    private String especie;
    private double edad;
    private String tamano;
    private String color;
    private String sexo;
    private String imagenUrl;
    private Part fotoSubida;

    @PostConstruct
    public void init() {
        // 1. Carga inicial de opciones para el menú desplegable (Dropdown)
        especiesDisponibles = Arrays.asList("Canino", "Felino", "Ave", "Conejo", "Roedor", "Reptil", "Otro");

        // 2. Validación de seguridad en capa de vista y carga transaccional
        if (usuarioSession != null && usuarioSession.isAdmin()) {
            cargarDatos();
        } else {
            FacesUtil.addError("Acceso Restringido", "No tienes permisos de administrador para visualizar este panel.");
        }
    }

    /**
     * Consulta directamente a PostgreSQL para alimentar las tablas del panel.
     */
    public void cargarDatos() {
        this.censoMascotas = mascotaRepository.listarTodos();
        this.solicitudesPendientes = solicitudRepository.buscarPendientes();
        this.solicitudes = this.solicitudesPendientes; // Sincronizamos para evitar PropertyNotFoundException en el XHTML
    }

    /**
     * Crea, procesa imágenes y persiste una nueva mascota en la base de datos.
     * AGREGADA @Transactional PARA QUE LOS ADOPTANTES LA VEAN DE INMEDIATO EN EL CATÁLOGO.
     */
    @Transactional
    public void registrarMascota() {
        // Blindaje de seguridad en el backend
        if (usuarioSession == null || !usuarioSession.isAdmin()) {
            FacesUtil.addError("Sesión inválida", "Debe iniciar sesión como administrador.");
            return;
        }

        if (nombre == null || nombre.trim().isEmpty() || especie == null || especie.trim().isEmpty()) {
            FacesUtil.addWarn("Campos Incompletos", "El nombre y la especie son obligatorios para el registro.");
            return;
        }

        try {
            // Instanciamos el modelo listo para JPA asegurando que nazca DISPONIBLE
            Mascota nueva = new Mascota(
                    nombre.trim(),
                    especie.trim(),
                    edad,
                    tamano != null && !tamano.isBlank() ? tamano : "Mediano",
                    sexo != null && !sexo.isBlank() ? sexo : "Desconocido",
                    color != null && !color.isBlank() ? color : "Mestizo",
                    EstadoMascota.DISPONIBLE
            );

            // 3. Procesamiento inteligente de imagen (Prioriza archivo subido, luego URL externa)
            if (fotoSubida != null && fotoSubida.getSize() > 0) {
                try {
                    String nombreArchivo = UUID.randomUUID().toString() + "_" + fotoSubida.getSubmittedFileName();
                    String rutaDirectorio = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagenes/");
                    File directorio = new File(rutaDirectorio);

                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }

                    File archivoDestino = new File(directorio, nombreArchivo);
                    try (InputStream input = fotoSubida.getInputStream()) {
                        Files.copy(input, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }

                    // --- SOLUCIÓN DE RUTAS ABSOLUTAS ---
                    // Obtenemos la raíz de la aplicación (/Pawsity) para que la imagen cargue desde cualquier vista
                    String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    nueva.setImagenUrl(contextPath + "/resources/imagenes/" + nombreArchivo);
                    // -----------------------------------

                } catch (Exception e) {
                    FacesUtil.addError("Error de Archivo", "No se pudo guardar la imagen en el servidor: " + e.getMessage());
                    return;
                }
            } else if (imagenUrl != null && !imagenUrl.isBlank()) {
                nueva.setImagenUrl(imagenUrl.trim());
            } else {
                // Si el admin no pone ninguna foto, podemos asignar una por defecto usando el contexto
                String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                nueva.setImagenUrl("https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=600&auto=format&fit=crop");
            }

            // Guardado transaccional en PostgreSQL vía JPA
            mascotaRepository.guardar(nueva);
            FacesUtil.addInfo("¡Registro Exitoso!", nombre + " ha sido ingresado al catálogo del refugio.");

            // Limpieza de memoria y recarga visual de la tabla
            limpiar();
            cargarDatos();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesUtil.addError("Error de Guardado", "Ocurrió un problema en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Aprueba o rechaza una adopción sincronizando ambas tablas en una misma transacción.
     * AGREGADA @Transactional PARA ASEGURAR CAMBIOS EN LA MASCOTA Y EN LA SOLICITUD.
     */
    @Transactional
    public void gestionar(SolicitudDeAdopcion sol, boolean aprobar) {
        if (usuarioSession == null || !usuarioSession.isAdmin()) {
            FacesUtil.addError("Sesión inválida", "Debe iniciar sesión como administrador.");
            return;
        }

        if (sol == null) {
            FacesUtil.addWarn("Atención", "No se ha seleccionado ninguna solicitud válida.");
            return;
        }

        try {
            if (aprobar) {
                sol.aprobar(); // Cambia solicitud a APROBADO y mascota a ADOPTADO
                FacesUtil.addInfo("Solicitud Aprobada", "La adopción de " + sol.getMascota().getNombre() + " ha sido oficializada.");
            } else {
                sol.rechazar(); // Cambia solicitud a RECHAZADA y mascota a DISPONIBLE
                FacesUtil.addWarn("Solicitud Rechazada", "La mascota " + sol.getMascota().getNombre() + " vuelve al catálogo.");
            }

            // Sincronización JPA en base de datos
            solicitudRepository.actualizar(sol);
            mascotaRepository.actualizar(sol.getMascota());

            // Recarga las listas para la pantalla
            cargarDatos();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesUtil.addError("Error Transaccional", "No se pudo completar el trámite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiar() {
        this.nombre = null;
        this.especie = null;
        this.edad = 0;
        this.tamano = null;
        this.color = null;
        this.sexo = null;
        this.imagenUrl = null;
        this.fotoSubida = null;
    }

    // --- GETTERS Y SETTERS COMPLETOS PARA PRIMEFACES ---
    public List<Mascota> getCensoMascotas() { return censoMascotas; }
    public void setCensoMascotas(List<Mascota> censoMascotas) { this.censoMascotas = censoMascotas; }

    public List<SolicitudDeAdopcion> getSolicitudesPendientes() { return solicitudesPendientes; }
    public void setSolicitudesPendientes(List<SolicitudDeAdopcion> solicitudesPendientes) { this.solicitudesPendientes = solicitudesPendientes; }

    public List<SolicitudDeAdopcion> getSolicitudes() { return solicitudes; }
    public void setSolicitudes(List<SolicitudDeAdopcion> solicitudes) { this.solicitudes = solicitudes; }

    public List<String> getEspeciesDisponibles() { return especiesDisponibles; }
    public void setEspeciesDisponibles(List<String> especiesDisponibles) { this.especiesDisponibles = especiesDisponibles; }

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
    public Part getFotoSubida() { return fotoSubida; }
    public void setFotoSubida(Part fotoSubida) { this.fotoSubida = fotoSubida; }
}