package ec.edu.unl.pawsity.repositorios;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;

public abstract class GenericRepository<T> {

    @Inject
    protected EntityManager em;

    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void guardar(T entity) {
        Objects.requireNonNull(entity, "La entidad a guardar no puede ser nula");
        em.persist(entity);
    }

    @Transactional
    public T actualizar(T entity) {
        Objects.requireNonNull(entity, "La entidad a actualizar no puede ser nula");
        return em.merge(entity);
    }

    @Transactional
    public void eliminar(T entity) {
        Objects.requireNonNull(entity, "La entidad a eliminar no puede ser nula");
        T toRemove = em.merge(entity);
        em.remove(toRemove);
    }

    public T buscarPorId(Long id) {
        if (id == null) return null;
        return em.find(entityClass, id);
    }

    public List<T> listarTodos() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }
}
