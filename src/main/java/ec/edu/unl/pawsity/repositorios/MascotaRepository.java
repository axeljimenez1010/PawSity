package ec.edu.unl.pawsity.repositorios;

import ec.edu.unl.pawsity.dominio.mascota.EstadoMascota;
import ec.edu.unl.pawsity.dominio.mascota.Mascota;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class MascotaRepository {

    @Inject
    private EntityManager em;

    // --- MÉTODO AGREGADO: SOLUCIONA EL ERROR DE COMPILACIÓN EN AdopcionService ---
    public Mascota buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Mascota.class, id);
    }
    // -----------------------------------------------------------------------------

    // --- NUEVO MÉTODO: FILTRA MASCOTAS ADOPTADAS PARA LIMPIAR LA COLA VETERINARIA ---
    public List<Mascota> buscarActivasParaVeterinario() {
        return em.createQuery(
                        "SELECT m FROM Mascota m WHERE m.estado <> :estado ORDER BY m.id DESC",
                        Mascota.class)
                .setParameter("estado", EstadoMascota.ADOPTADO)
                .setHint("jakarta.persistence.cache.storeMode", "BYPASS")
                .getResultList();
    }
    // -----------------------------------------------------------------------------

    public List<Mascota> buscarDisponibles() {
        return em.createQuery(
                        "SELECT m FROM Mascota m WHERE m.estado = :estado ORDER BY m.fechaIngreso ASC",
                        Mascota.class)
                .setParameter("estado", EstadoMascota.DISPONIBLE)
                .setHint("jakarta.persistence.cache.storeMode", "BYPASS")
                .getResultList();
    }

    public List<Mascota> listarTodos() {
        return em.createQuery("SELECT m FROM Mascota m ORDER BY m.id DESC", Mascota.class)
                .setHint("jakarta.persistence.cache.storeMode", "BYPASS")
                .getResultList();
    }

    public void guardar(Mascota mascota) {
        try {
            em.getTransaction().begin();
            if (mascota.getId() == null) {
                em.persist(mascota);
            } else {
                em.merge(mascota);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void actualizar(Mascota mascota) {
        try {
            em.getTransaction().begin();
            em.merge(mascota);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
