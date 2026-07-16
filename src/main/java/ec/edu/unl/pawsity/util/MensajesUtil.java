package ec.edu.unl.pawsity.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class MensajesUtil {
    public static void mostrarExito(String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle));
    }
    public static void mostrarError(String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalle));
    }
    public static void mostrarAdvertencia(String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, detalle));
    }
}
