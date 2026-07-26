package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.gestionrefugio.EstadoSolicitud;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import ec.edu.unl.pawsity.repositorios.MascotaRepository;
import ec.edu.unl.pawsity.repositorios.SolicitudRepository;
import ec.edu.unl.pawsity.util.FacesUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional; // <-- IMPORTACIÓN NECESARIA PARA JPA
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named("adoptanteBean")
@ViewScoped
public class AdoptanteBean implements Serializable {

    @Inject private MascotaRepository mascotaRepository;
    @Inject private SolicitudRepository solicitudRepository;
    @Inject private UsuarioSession usuarioSession;

    private List<Mascota> catalogoCompleto;
    private List<Mascota> catalogoFiltrado;

    private String filtroEspecie = "todos";
    private String filtroTamano = "todos";
    private String filtroSexo = "todos";
    private String filtroColor = "";

    private Mascota mascotaHistorial;
    private Mascota mascotaSeleccionada;

    private String tipoVivienda;
    private boolean tieneOtrasMascotas;
    private String experienciaPrevia;
    private String motivoAdopcion;

    @PostConstruct
    public void init() {
        cargarCatalogo();
    }

    public void cargarCatalogo() {
        try {
            List<Mascota> disponibles = mascotaRepository.buscarDisponibles();

            this.catalogoCompleto = disponibles.stream()
                    .sorted(Comparator.comparing(Mascota::getFechaIngreso, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());

            filtrar();
        } catch (Exception e) {
            FacesUtil.addError("Error de Carga", "No se pudo recuperar el catálogo de la base de datos.");
        }
    }

    public void filtrar() {
        if (catalogoCompleto == null) return;

        catalogoFiltrado = catalogoCompleto.stream()
                .filter(m -> filtroEspecie == null || "todos".equalsIgnoreCase(filtroEspecie) || m.getEspecie().equalsIgnoreCase(filtroEspecie))
                .filter(m -> filtroTamano == null || "todos".equalsIgnoreCase(filtroTamano) || m.getTamano().equalsIgnoreCase(filtroTamano))
                .filter(m -> filtroSexo == null || "todos".equalsIgnoreCase(filtroSexo) || m.getSexo().equalsIgnoreCase(filtroSexo))
                .filter(m -> filtroColor == null || filtroColor.isBlank() || m.getColor().toLowerCase().contains(filtroColor.trim().toLowerCase()))
                .collect(Collectors.toList());
    }

    public void resetFiltros() {
        this.filtroEspecie = "todos";
        this.filtroTamano = "todos";
        this.filtroSexo = "todos";
        this.filtroColor = "";
        filtrar();
    }

    public void verHistorial(Mascota m) {
        this.mascotaHistorial = m;
    }

    public void abrirFormulario(Mascota m) {
        this.mascotaSeleccionada = m;
        this.tipoVivienda = null;
        this.tieneOtrasMascotas = false;
        this.experienciaPrevia = "";
        this.motivoAdopcion = "";
    }

    @Transactional
    public void confirmarSolicitud() {
        if (mascotaSeleccionada == null) {
            FacesUtil.addWarn("Atención", "No se ha seleccionado ninguna mascota.");
            return;
        }

        if (mascotaSeleccionada.getEstado() != EstadoMascota.DISPONIBLE) {
            FacesUtil.addWarn("No disponible", "Esta mascota ya está en proceso de adopción por otra persona.");
            cargarCatalogo();
            return;
        }

        if (usuarioSession == null || !usuarioSession.isAdoptante()) {
            FacesUtil.addError("Sesión inválida", "Debe iniciar sesión como un adoptante registrado para realizar este trámite.");
            return;
        }

        try {
            Adoptante adoptanteActual = (Adoptante) usuarioSession.getUsuarioActual();

            mascotaSeleccionada.setEstado(EstadoMascota.EN_PROCESO);
            mascotaRepository.actualizar(mascotaSeleccionada);

            SolicitudDeAdopcion nuevaSolicitud = new SolicitudDeAdopcion(adoptanteActual, mascotaSeleccionada);
            nuevaSolicitud.registrarFormulario(tipoVivienda, tieneOtrasMascotas, experienciaPrevia, motivoAdopcion);

            nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

            solicitudRepository.guardar(nuevaSolicitud);

            FacesUtil.addInfo("¡Solicitud Enviada!", "Has solicitado adoptar a " + mascotaSeleccionada.getNombre() + ". La administración revisará tu formulario en breve.");

            cargarCatalogo();

        } catch (Exception e) {
            FacesUtil.addError("Error de Procesamiento", "Ocurrió un error al enviar tu solicitud: " + e.getMessage());
        }
    }


    public List<Mascota> getCatalogoFiltrado() { return catalogoFiltrado; }
    public void setCatalogoFiltrado(List<Mascota> catalogoFiltrado) { this.catalogoFiltrado = catalogoFiltrado; }

    public String getFiltroEspecie() { return filtroEspecie; }
    public void setFiltroEspecie(String filtroEspecie) { this.filtroEspecie = filtroEspecie; }
    public String getFiltroTamano() { return filtroTamano; }
    public void setFiltroTamano(String filtroTamano) { this.filtroTamano = filtroTamano; }
    public String getFiltroSexo() { return filtroSexo; }
    public void setFiltroSexo(String filtroSexo) { this.filtroSexo = filtroSexo; }
    public String getFiltroColor() { return filtroColor; }
    public void setFiltroColor(String filtroColor) { this.filtroColor = filtroColor; }

    public Mascota getMascotaHistorial() { return mascotaHistorial; }
    public void setMascotaHistorial(Mascota mascotaHistorial) { this.mascotaHistorial = mascotaHistorial; }
    public Mascota getMascotaSeleccionada() { return mascotaSeleccionada; }
    public void setMascotaSeleccionada(Mascota mascotaSeleccionada) { this.mascotaSeleccionada = mascotaSeleccionada; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }
    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public void setTieneOtrasMascotas(boolean tieneOtrasMascotas) { this.tieneOtrasMascotas = tieneOtrasMascotas; }
    public String getExperienciaPrevia() { return experienciaPrevia; }
    public void setExperienciaPrevia(String experienciaPrevia) { this.experienciaPrevia = experienciaPrevia; }
    public String getMotivoAdopcion() { return motivoAdopcion; }
    public void setMotivoAdopcion(String motivoAdopcion) { this.motivoAdopcion = motivoAdopcion; }
}