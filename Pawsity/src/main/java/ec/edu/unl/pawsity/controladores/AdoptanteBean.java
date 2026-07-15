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
import java.util.List;
import java.util.stream.Collectors;

@Named("adoptanteBean")
@ViewScoped
public class AdoptanteBean implements Serializable {
    private List<Mascota> catalogoCompleto;
    private List<Mascota> catalogoFiltrado;
    private String filtroEspecie = "todos";

    @Inject private RefugioService refugioService;
    @Inject private LoginBean loginBean;

    @PostConstruct
    public void init() { cargarCatalogo(); }

    public void cargarCatalogo() {
        this.catalogoCompleto = refugioService.getRefugio().buscarMascota().stream()
                .filter(m -> m.getEstado() == EstadoMascota.DISPONIBLE)
                .collect(Collectors.toList());
        filtrar();
    }

    public void filtrar() {
        if (filtroEspecie == null || filtroEspecie.equalsIgnoreCase("todos")) {
            catalogoFiltrado = catalogoCompleto;
        } else {
            catalogoFiltrado = catalogoCompleto.stream()
                    .filter(m -> m.getEspecie().equalsIgnoreCase(filtroEspecie))
                    .collect(Collectors.toList());
        }
    }

    public void solicitar(Mascota mascota) {
        if (mascota.getEstado() != EstadoMascota.DISPONIBLE) {
            MensajesUtil.mostrarAdvertencia("No disponible", "Esta mascota ya está en proceso de adopción.");
            cargarCatalogo(); return;
        }
        Adoptante adoptante = (Adoptante) loginBean.getUsuarioLogueado();
        mascota.setEstado(EstadoMascota.EN_PROCESO);
        refugioService.getSistemaDeSolicitudes().add(new SolicitudDeAdopcion(adoptante, mascota));
        MensajesUtil.mostrarExito("¡Solicitud Enviada!", "Has solicitado adoptar a " + mascota.getNombre() + ".");
        cargarCatalogo();
    }

    public List<Mascota> getCatalogoFiltrado() { return catalogoFiltrado; }
    public String getFiltroEspecie() { return filtroEspecie; }
    public void setFiltroEspecie(String filtroEspecie) { this.filtroEspecie = filtroEspecie; }
}