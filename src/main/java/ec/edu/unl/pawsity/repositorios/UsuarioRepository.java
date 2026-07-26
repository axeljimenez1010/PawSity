package ec.edu.unl.pawsity.repositorios;

import ec.edu.unl.pawsity.dominio.usuarios.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class UsuarioRepository extends GenericRepository<Usuario> {

    @Inject
    private EntityManager em;

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Usuario buscarPorCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) return null;
        try {
            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByCorreo", Usuario.class);
            query.setParameter("correo", correo.trim().toLowerCase());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario autenticar(String correo, String contrasenaPlana) {
        Usuario usuario = buscarPorCorreo(correo);
        if (usuario != null && usuario.iniciarSesion(correo, contrasenaPlana)) {
            return usuario;
        }
        return null;
    }
}