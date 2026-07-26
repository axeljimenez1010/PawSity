package ec.edu.unl.pawsity.repositorios;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    private EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        // Al usar RESOURCE_LOCAL, creamos la fábrica explícitamente
        // usando el nombre exacto de nuestra unidad en persistence.xml
        this.emf = Persistence.createEntityManagerFactory("PawsityPU");
    }

    @Produces
    @RequestScoped
    public EntityManager crearEntityManager() {
        // Ahora emf nunca será null y creará la sesión sin problemas
        return emf.createEntityManager();
    }

    public void cerrarEntityManager(@Disposes EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @PreDestroy
    public void cerrarFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
