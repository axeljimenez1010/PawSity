package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.usuarios.Administrador;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import ec.edu.unl.pawsity.dominio.usuarios.Usuario;
import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import ec.edu.unl.pawsity.repositorios.UsuarioRepository;
import ec.edu.unl.pawsity.util.FacesUtil;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private String correo;
    private String contrasena;

    // Inyección del repositorio JPA para consultar PostgreSQL
    @Inject
    private UsuarioRepository usuarioRepository;

    // Inyección del gestor centralizado de sesión activa
    @Inject
    private UsuarioSession usuarioSession;

    public String login() {
        System.out.println("DEBUG: Intentando autenticar correo: " + correo);

        // Autenticación real contra la base de datos usando contraseñas con hash SHA-256
        Usuario usuarioAutenticado = usuarioRepository.autenticar(correo, contrasena);

        if (usuarioAutenticado != null) {
            System.out.println("DEBUG: Usuario autenticado correctamente.");
            System.out.println("DEBUG: Nombre recuperado del usuario: '" + usuarioAutenticado.getNombres() + "'");

            // Guardamos el usuario activo en el alcance de sesión (SessionScoped)
            usuarioSession.iniciar(usuarioAutenticado);

            // Redirección inteligente basada en el rol polimórfico del usuario
            if (usuarioAutenticado instanceof Administrador) {
                return "/admin/panel-admin?faces-redirect=true";
            } else if (usuarioAutenticado instanceof Veterinario) {
                return "/vet/modulo-vet?faces-redirect=true";
            } else if (usuarioAutenticado instanceof Adoptante) {
                return "/adoptante/catalogo?faces-redirect=true";
            }
        }

        // Alerta visual flotante (Growl) uniforme con el estándar del sistema
        FacesUtil.addError("Acceso Denegado", "Correo o contraseña incorrectos.");
        return null;
    }

    public String logout() {
        usuarioSession.cerrar();
        jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    /**
     * Método de compatibilidad retroactiva:
     * Si alguna vista .xhtml existente llama a #{loginBean.usuarioLogueado},
     * lo redirige de forma transparente al gestor de sesión actual.
     */
    public Usuario getUsuarioLogueado() {
        return usuarioSession.getUsuarioActual();
    }

    // --- GETTERS Y SETTERS ---
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
