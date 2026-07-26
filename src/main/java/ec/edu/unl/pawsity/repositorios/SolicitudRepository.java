package ec.edu.unl.pawsity.repositorios;

import ec.edu.unl.pawsity.dominio.gestionrefugio.EstadoSolicitud;
import ec.edu.unl.pawsity.dominio.gestionrefugio.SolicitudDeAdopcion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class SolicitudRepository extends GenericRepository<SolicitudDeAdopcion> {

    @Inject
    private EntityManager em;

    public SolicitudRepository() {
        super(SolicitudDeAdopcion.class);
    }

    /**
     * Busca las solicitudes pendientes evadiendo el caché de EclipseLink
     * para que el administrador las vea instantáneamente en su panel.
     */
    public List<SolicitudDeAdopcion> buscarPendientes() {
        TypedQuery<SolicitudDeAdopcion> query = em.createNamedQuery("SolicitudDeAdopcion.findByEstado", SolicitudDeAdopcion.class);
        query.setParameter("estado", EstadoSolicitud.PENDIENTE);

        // --- INSTRUCCIÓN ANTI-CACHÉ ---
        query.setHint("jakarta.persistence.cache.storeMode", "BYPASS");

        return query.getResultList();
    }

    public List<SolicitudDeAdopcion> buscarPorAdoptante(Long idAdoptante) {
        if (idAdoptante == null) {
            return List.of();
        }
        TypedQuery<SolicitudDeAdopcion> query = em.createNamedQuery("SolicitudDeAdopcion.findByAdoptante", SolicitudDeAdopcion.class);
        query.setParameter("idAdoptante", idAdoptante);

        // --- INSTRUCCIÓN ANTI-CACHÉ ---
        query.setHint("jakarta.persistence.cache.storeMode", "BYPASS");

        return query.getResultList();
    }

    /**
     * Respetamos la firma void de GenericRepository para guardar
     * e incluimos el COMMIT manual para escribir en PostgreSQL.
     */
    @Override
    public void guardar(SolicitudDeAdopcion solicitud) {
        try {
            em.getTransaction().begin();
            if (solicitud.getId() == null) {
                em.persist(solicitud);
            } else {
                em.merge(solicitud);
            }
            em.getTransaction().commit(); // <-- FUERZA EL GUARDADO INMEDIATO EN LA BD
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Respetamos la firma que devuelve el objeto (T) para la actualización.
     */
    @Override
    public SolicitudDeAdopcion actualizar(SolicitudDeAdopcion solicitud) {
        try {
            em.getTransaction().begin();
            SolicitudDeAdopcion actualizada = em.merge(solicitud);
            em.getTransaction().commit(); // <-- FUERZA LA ACTUALIZACIÓN EN LA BD
            return actualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
