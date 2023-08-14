package org.example.dao_crud;

import jakarta.persistence.*;
import org.example.JpaService;
import org.example.xml_marshal.xml_entities.XmlClient;
import org.example.models.Client;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class DaoClient implements DAO<Client> {
    DaoVetRegister daoVetRegister = new DaoVetRegister();

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public boolean constraintCheck(long id) {
//        return this.get(id).ge.stream().filter(p -> p.getClient().getId() == id).collect(Collectors.toList()).size() == 0 &&
//                daoVetRegister.getAll().stream().filter(vr -> vr.getClient().getId() == id).collect(Collectors.toList()).size() == 0;
    return true;
    }

    @Override
    public void save(Client client) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            boolean successfulPersist = false;
            transaction.begin();
            try {
                entityManager.persist(client);
                successfulPersist = true;

            } finally {
                if (successfulPersist) transaction.commit();
                else transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(entityManager.find(Client.class, id));
    }

    @Override
    public List<Client> getAll() {
        Query query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    @Override
    public void update(Client client) {
        executeInsideTransaction(entityManager -> entityManager.merge(client));
    }

    // for updating database object from xml object
    public void update(XmlClient xmlClient) {
        Client c = (Client) this.get(xmlClient.getId()).orElse(null);
        c.setFirstName(xmlClient.getFirstName());
        c.setLastName(xmlClient.getLastName());
        c.setEmail(xmlClient.getEmail());
        c.setPhone(xmlClient.getPhone());
        executeInsideTransaction(entityManager -> entityManager.merge(c));
    }

    @Override
    public void delete(Client client) {
        Client persistentInstance = entityManager.merge(client);
        executeInsideTransaction(entityManager -> entityManager.remove(persistentInstance));
    }

    public Client getById(String str) {
        Query query = entityManager.createQuery(
                String.format("from Client as c where c.id = %s", str), Client.class);
        if (!query.getResultList().isEmpty())
            return (Client) query.getResultList().get(0);
        return null;
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (ConstraintViolationException e) {
            tx.rollback();
            System.out.println("ConstraintViolationException!!! You can't delete cause of constraints!");
//            throw e;
        } catch (PersistenceException e) {
            tx.rollback();
            System.out.println("PersistenceException!!! Can not update because of constraints!");
//            throw e;
        }
    }

    public Client newData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide some data for new Client..");
        System.out.println("Enter the first Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter the last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter the email:");
        String email = scanner.nextLine();
        System.out.println("Enter the phone:");
        String phone = scanner.nextLine();
        Client client = new Client(firstName, lastName, email, phone);
        return client;
    }
}
