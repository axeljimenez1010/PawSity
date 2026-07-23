package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.usuarios.*;
import ec.edu.unl.pawsity.servicios.RefugioService;
import ec.edu.unl.pawsity.util.MensajesUtil;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String correo;
    private String contrasena;
    private Usuario usuarioLogueado;

    @Inject
    private RefugioService refugioService;

    public String login() {
        usuarioLogueado = refugioService.autenticar(correo, contrasena);

        if (usuarioLogueado != null) {
            // --- LÍNEA DE DEPURACIÓN AÑADIDA ---
            System.out.println("DEBUG: Usuario autenticado correctamente.");
            System.out.println("DEBUG: Nombre recuperado del usuario: '" + usuarioLogueado.getNombres() + "'");
            // ------------------------------------

            if (usuarioLogueado instanceof Administrador) return "/admin/panel-admin?faces-redirect=true";
            if (usuarioLogueado instanceof Veterinario) return "/vet/modulo-vet?faces-redirect=true";
            if (usuarioLogueado instanceof Adoptante) return "/adoptante/catalogo?faces-redirect=true";
        }

        MensajesUtil.mostrarError("Acceso Denegado", "Correo o contraseña incorrectos.");
        return null;
    }

    public String logout() {
        jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public Usuario getUsuarioLogueado() { return usuarioLogueado; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
