package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import ec.edu.unl.pawsity.dominio.mascota.*;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import ec.edu.unl.pawsity.servicios.RefugioService;
import ec.edu.unl.pawsity.util.MensajesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named("adoptanteBean")
@ViewScoped
public class AdoptanteBean implements Serializable {
    private List<Mascota> catalogoCompleto;
    private List<Mascota> catalogoFiltrado;

    // Filtros disponibles para el adoptante
    private String filtroEspecie = "todos";
    private String filtroTamano = "todos";
    private String filtroSexo = "todos";
    private String filtroColor = "";

    // Mascota sobre la que se abrió el diálogo de "Antecedentes médicos"
    private Mascota mascotaHistorial;

    // Mascota sobre la que se abrió el diálogo de "Formulario de adopción"
    private Mascota mascotaSeleccionada;
    private String tipoVivienda;
    private boolean tieneOtrasMascotas;
    private String experienciaPrevia;
    private String motivoAdopcion;

    @Inject private RefugioService refugioService;
    @Inject private LoginBean loginBean;

    @PostConstruct
    public void init() { cargarCatalogo(); }

    public void cargarCatalogo() {
        // Se ordena de la mascota que más tiempo lleva esperando (fechaIngreso más antigua)
        // a la más reciente. El orden se conserva luego al filtrar, y la vista usa esa
        // posición para darle mayor tamaño de imagen a quien más tiempo lleva en el refugio.
        this.catalogoCompleto = refugioService.getRefugio().buscarMascota().stream()
                .filter(m -> m.getEstado() == EstadoMascota.DISPONIBLE)
                .sorted(Comparator.comparing(Mascota::getFechaIngreso))
                .collect(Collectors.toList());
        filtrar();
    }

    public void filtrar() {
        catalogoFiltrado = catalogoCompleto.stream()
                .filter(m -> filtroEspecie == null || "todos".equalsIgnoreCase(filtroEspecie) || m.getEspecie().equalsIgnoreCase(filtroEspecie))
                .filter(m -> filtroTamano == null || "todos".equalsIgnoreCase(filtroTamano) || m.getTamano().equalsIgnoreCase(filtroTamano))
                .filter(m -> filtroSexo == null || "todos".equalsIgnoreCase(filtroSexo) || m.getSexo().equalsIgnoreCase(filtroSexo))
                .filter(m -> filtroColor == null || filtroColor.isBlank() || m.getColor().toLowerCase().contains(filtroColor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void resetFiltros() {
        filtroEspecie = "todos";
        filtroTamano = "todos";
        filtroSexo = "todos";
        filtroColor = "";
        filtrar();
    }

    // --- Diálogo de antecedentes médicos (vacunas y desparasitación) ---
    public void verHistorial(Mascota m) {
        this.mascotaHistorial = m;
    }

    // --- Diálogo del formulario de adopción ---
    public void abrirFormulario(Mascota m) {
        this.mascotaSeleccionada = m;
        this.tipoVivienda = null;
        this.tieneOtrasMascotas = false;
        this.experienciaPrevia = "";
        this.motivoAdopcion = "";
    }

    public void confirmarSolicitud() {
        if (mascotaSeleccionada == null) return;

        if (mascotaSeleccionada.getEstado() != EstadoMascota.DISPONIBLE) {
            MensajesUtil.mostrarAdvertencia("No disponible", "Esta mascota ya está en proceso de adopción.");
            cargarCatalogo();
            return;
        }
        if (!(loginBean.getUsuarioLogueado() instanceof Adoptante adoptante)) {
            MensajesUtil.mostrarError("Sesión inválida", "Debe iniciar sesión como adoptante.");
            return;
        }

        mascotaSeleccionada.setEstado(EstadoMascota.EN_PROCESO);
        SolicitudDeAdopcion nueva = new SolicitudDeAdopcion(adoptante, mascotaSeleccionada);
        nueva.registrarFormulario(tipoVivienda, tieneOtrasMascotas, experienciaPrevia, motivoAdopcion);
        refugioService.getSistemaDeSolicitudes().add(nueva);

        MensajesUtil.mostrarExito("¡Solicitud Enviada!", "Has solicitado adoptar a " + mascotaSeleccionada.getNombre() + ".");
        cargarCatalogo();
    }

    public List<Mascota> getCatalogoFiltrado() { return catalogoFiltrado; }

    public String getFiltroEspecie() { return filtroEspecie; }
    public void setFiltroEspecie(String filtroEspecie) { this.filtroEspecie = filtroEspecie; }
    public String getFiltroTamano() { return filtroTamano; }
    public void setFiltroTamano(String filtroTamano) { this.filtroTamano = filtroTamano; }
    public String getFiltroSexo() { return filtroSexo; }
    public void setFiltroSexo(String filtroSexo) { this.filtroSexo = filtroSexo; }
    public String getFiltroColor() { return filtroColor; }
    public void setFiltroColor(String filtroColor) { this.filtroColor = filtroColor; }

    public Mascota getMascotaHistorial() { return mascotaHistorial; }
    public Mascota getMascotaSeleccionada() { return mascotaSeleccionada; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }
    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public void setTieneOtrasMascotas(boolean tieneOtrasMascotas) { this.tieneOtrasMascotas = tieneOtrasMascotas; }
    public String getExperienciaPrevia() { return experienciaPrevia; }
    public void setExperienciaPrevia(String experienciaPrevia) { this.experienciaPrevia = experienciaPrevia; }
    public String getMotivoAdopcion() { return motivoAdopcion; }
    public void setMotivoAdopcion(String motivoAdopcion) { this.motivoAdopcion = motivoAdopcion; }
}