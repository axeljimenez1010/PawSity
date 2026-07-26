package ec.edu.unl.pawsity.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class FacesUtil {

    public static void addInfo(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje);
    }

    public static void addWarn(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje);
    }

    public static void addError(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje);
    }

    private static void addMessage(FacesMessage.Severity severidad, String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severidad, titulo, mensaje));
        }
    }
}
