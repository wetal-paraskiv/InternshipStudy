package org.example.dao_crud;

import jakarta.persistence.*;
import org.example.JpaService;
import org.example.xml_marshal.xml_entities.XmlVetRegister;
import org.example.models.VetRegister;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class DaoVetRegister implements DAO<VetRegister> {

    private static JpaService jpaService = JpaService.getInstance();
    private EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void save(VetRegister register) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();

            boolean successfulPersist = false;
            transaction.begin();

            try {
                entityManager.persist(register);
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
        return Optional.ofNullable(entityManager.find(VetRegister.class, id));
    }


    @Override
    public List<VetRegister> getAll() {
        Query query = entityManager.createQuery("SELECT vr FROM VetRegister vr", VetRegister.class);
        return query.getResultList();
    }

    public List<VetRegister> filterRegistersByDoctorId(Long doctorId){
        return entityManager.createQuery("select o from VetRegister o " +
                        "where o.doctor.id = ?1")
                .setParameter(1, doctorId)
                .getResultList();
    }

    @Override
    public void update(VetRegister vetRegister) {
        executeInsideTransaction(entityManager -> entityManager.merge(vetRegister));
    }

    public void update(XmlVetRegister xmlVetRegister) {
        VetRegister vr = (VetRegister) this.get(xmlVetRegister.getId()).orElse(null);
        vr.setDate(xmlVetRegister.getDate());
        vr.setDoctor(xmlVetRegister.getDoctor());
        vr.setPet(xmlVetRegister.getPet());
        vr.setClient(xmlVetRegister.getClient());
        vr.setVetService(xmlVetRegister.getVetService());
        executeInsideTransaction(entityManager -> entityManager.merge(vr));
    }

    @Override
    public void delete(VetRegister register) {
        VetRegister persistentInstance = entityManager.merge(register);
        executeInsideTransaction(entityManager -> entityManager.remove(persistentInstance));
    }


    public List<VetRegister> registersWhereDoctorNameLikeClientName(String nameLike) {
        Query query = entityManager.createQuery(
                "FROM VetRegister as vr " +
                "where lower(vr.client.firstName) like lower(concat(concat('%', :nameLike), '%'))" +
                "and lower(vr.doctor.firstName) like lower(concat(concat('%', :nameLike), '%'))", VetRegister.class);
        query.setParameter("nameLike", nameLike);
        return query.getResultList();
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

    public VetRegister newData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide some data for new register..");
        System.out.println("Enter the Doctor ID:");
        Long doctorId = Long.parseLong(scanner.nextLine().trim());
        System.out.println("Enter the Client ID:");
        Long clientId = Long.parseLong(scanner.nextLine().trim());
        System.out.println("Enter the Pet ID:");
        Long petId = Long.parseLong(scanner.nextLine().trim());
        System.out.println("Enter the Vet Service ID:");
        Long vetServiceId = Long.parseLong(scanner.nextLine().trim());
        VetRegister vetRegister = new VetRegister();
        return vetRegister;
    }

}

