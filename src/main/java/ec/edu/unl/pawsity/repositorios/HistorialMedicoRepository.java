package ec.edu.unl.pawsity.repositorios;

import ec.edu.unl.pawsity.dominio.mascota.HistorialMedico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class HistorialMedicoRepository extends GenericRepository<HistorialMedico> {

    @Inject
    private EntityManager em;

    public HistorialMedicoRepository() {
        super(HistorialMedico.class);
    }

    public HistorialMedico buscarPorMascota(Long idMascota) {
        if (idMascota == null) return null;
        try {
            // Reemplazamos createNamedQuery por createQuery con la sentencia JPQL directa
            TypedQuery<HistorialMedico> query = em.createQuery(
                    "SELECT h FROM HistorialMedico h WHERE h.mascota.id = :idMascota",
                    HistorialMedico.class
            );
            query.setParameter("idMascota", idMascota);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
