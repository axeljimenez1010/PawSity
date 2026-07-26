package ec.edu.unl.pawsity.controladores;

import ec.edu.unl.pawsity.dominio.usuarios.Administrador;
import ec.edu.unl.pawsity.dominio.usuarios.Adoptante;
import ec.edu.unl.pawsity.dominio.usuarios.Usuario;
import ec.edu.unl.pawsity.dominio.usuarios.Veterinario;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("usuarioSession")
@SessionScoped
public class UsuarioSession implements Serializable {

    private Usuario usuarioActual;

    public void iniciar(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrar() {
        this.usuarioActual = null;
    }

    public boolean isLogueado() {
        return usuarioActual != null;
    }

    public boolean isAdmin() {
        return usuarioActual instanceof Administrador;
    }

    public boolean isAdoptante() {
        return usuarioActual instanceof Adoptante;
    }

    public boolean isVeterinario() {
        return usuarioActual instanceof Veterinario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}