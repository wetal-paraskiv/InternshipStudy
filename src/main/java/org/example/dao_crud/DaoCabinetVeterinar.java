package org.example.dao_crud;

import jakarta.persistence.*;
import org.example.JpaService;
import org.example.xml_marshal.xml_entities.XmlCabinetVeterinar;
import org.example.models.CabinetVeterinar;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class DaoCabinetVeterinar implements DAO<CabinetVeterinar> {

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();


    @Override
    public void save(CabinetVeterinar c) {
        EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();

            boolean successfulPersist = false;
            transaction.begin();

            try {
                entityManager.persist(c);
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
        return Optional.ofNullable(entityManager.find(CabinetVeterinar.class, id));
    }

    @Override
    public List<CabinetVeterinar> getAll() {
        Query query = entityManager.createQuery("SELECT a FROM CabinetVeterinar a", CabinetVeterinar.class);
        return query.getResultList();
    }

    @Override
    public void update(CabinetVeterinar c) {
        executeInsideTransaction(entityManager -> entityManager.merge(c));
    }

    public void update(XmlCabinetVeterinar xmlCv) {
        DaoDoctor daoDoctor = new DaoDoctor();
        DaoVetService daoVetService = new DaoVetService();

        CabinetVeterinar cv = (CabinetVeterinar) this.get(xmlCv.getId()).orElse(null);
        cv.setName(xmlCv.getName());
        cv.setAddress(xmlCv.getAddress());
        cv.setEmail(xmlCv.getEmail());
        cv.setPhone(xmlCv.getPhone());
        // update doctors related cabinet
        xmlCv.getDoctors().forEach(xmlDoctor -> daoDoctor.update(xmlDoctor));
        // update vetService related cabinet
        xmlCv.getVetServices().forEach(xmlVetService -> daoVetService.update(xmlVetService));
        executeInsideTransaction(entityManager -> entityManager.merge(cv));
    }

    @Override
    public void delete(CabinetVeterinar c) {
        CabinetVeterinar persistentInstance = entityManager.merge(c);
        executeInsideTransaction(entityManager -> entityManager.remove(persistentInstance));
    }

    public CabinetVeterinar getById(String str) {
        Query query = entityManager.createQuery(
                String.format("from CabinetVeterinar as cv where cv.id = %s", str), CabinetVeterinar.class);
        if (!query.getResultList().isEmpty())
            return (CabinetVeterinar) query.getResultList().get(0);
        return null;
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

    public CabinetVeterinar newData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide some data for new Cabinet Veterinar..");
        System.out.println("Enter the name:");
        String name = scanner.nextLine();
        System.out.println("Enter the address:");
        String address = scanner.nextLine();
        System.out.println("Enter the phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter the email:");
        String email = scanner.nextLine();
        CabinetVeterinar cabinet_veterinar = new CabinetVeterinar(name, address, phone, email);
        return cabinet_veterinar;
    }


}
