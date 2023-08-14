package org.example.dao_crud;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.JpaService;
import org.example.xml_marshal.xml_entities.XmlVetService;
import org.example.models.VetService;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DaoVetService implements DAO<VetService> {

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void save(VetService vetService) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();

            boolean successfulPersist = false;
            transaction.begin();

            try {
                entityManager.persist(vetService);
                successfulPersist = true;

            } finally {
                if (successfulPersist) {
                    transaction.commit();
                } else transaction.rollback();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional get(long id) {
        Optional result = Optional.ofNullable(entityManager.find(VetService.class, id));
        return result;
    }

    @Override
    public List<VetService> getAll() {
        Query query = entityManager.createQuery("SELECT vs FROM VetService vs", VetService.class);
        return query.getResultList();
    }

    @Override
    public void update(VetService vetService) {
        executeInsideTransaction(entityManager -> entityManager.merge(vetService));
    }
    public void update(XmlVetService xmlVetService) {
        VetService vs = (VetService) this.get(xmlVetService.getId()).orElse(null);
        vs.setName(xmlVetService.getName());
        vs.setPrice(xmlVetService.getPrice());
        executeInsideTransaction(entityManager -> entityManager.merge(vs));
    }

    @Override
    public void delete(VetService vetService) {
        VetService persistentInstance = entityManager.merge(vetService);
        executeInsideTransaction(entityManager -> entityManager.remove(persistentInstance));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }


}


