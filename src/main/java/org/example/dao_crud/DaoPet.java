package org.example.dao_crud;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.JpaService;
import org.example.models.Pet;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DaoPet implements DAO<Pet> {

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void save(Pet pet) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            boolean successfulPersist = false;
            transaction.begin();
            try {
                entityManager.persist(pet);
                successfulPersist = true;

            } finally {
                if (successfulPersist) transaction.commit();
                else transaction.rollback();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(entityManager.find(Pet.class, id));
    }

    public Pet findByName(String name){
        return (Pet) entityManager.createQuery("select object(o) from Pet o " +
                        "where o.name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Pet> getAll() {
        Query query = entityManager.createQuery("SELECT p FROM Pet p", Pet.class);
        return query.getResultList();
    }

    @Override
    public void update(Pet pet) {
        executeInsideTransaction(entityManager -> entityManager.merge(pet));
    }

    @Override
    public void delete(Pet pet) {
        Pet persistentInstance = entityManager.merge(pet);
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

