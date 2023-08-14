package org.example.dao_crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.JpaService;
import org.example.xml_marshal.xml_entities.XmlDoctor;
import org.example.models.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DaoDoctor implements DAO<Doctor> {

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void save(Doctor doctor) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();

            boolean successfulPersist = false;
            transaction.begin();

            try {
                entityManager.persist(doctor);
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
        return Optional.ofNullable(entityManager.find(Doctor.class, id));
    }

    @Override
    public List<Doctor> getAll() {
        Query query = entityManager.createQuery("SELECT d FROM Doctor d", Doctor.class);
        return query.getResultList();
    }

    @Override
    public void update(Doctor doctor) {
        executeInsideTransaction(entityManager -> entityManager.merge(doctor));
    }
    public void update(XmlDoctor xmlDoctor) {
        Doctor d = (Doctor) this.get(xmlDoctor.getId()).orElse(null);
        d.setFirstName(xmlDoctor.getFirstName());
        d.setLastName(xmlDoctor.getLastName());
        d.setPhone(xmlDoctor.getPhone());
        d.setEmail(xmlDoctor.getEmail());
        executeInsideTransaction(entityManager -> entityManager.merge(d));
    }

    @Override
    public void delete(Doctor doctor) {
        Doctor persistentInstance = entityManager.merge(doctor);
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
