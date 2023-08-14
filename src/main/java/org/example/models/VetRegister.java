package org.example.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "VET_REGISTER")
public class VetRegister implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "REGISTER_DATE")
    private String date;
    @ManyToOne
    @JoinColumn(name="fk_doctor")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name="fk_pet")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name="fk_client")
    private Client client;
    @ManyToOne
    @JoinColumn(name="fk_vet_service")
    private VetService vetService;

    public VetRegister() {
    }

    public VetRegister(Doctor doctor, Pet pet, VetService vetService) {
        this.date = getLocalDate();
        this.doctor = doctor;
        this.pet = pet;
        this.client = pet.getClient();
        this.vetService = vetService;
    }

    private String getLocalDate() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public VetService getVetService() {
        return vetService;
    }
    public void setVetService(VetService vetService) {
        this.vetService = vetService;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "VetRegister " +
                "id=" + id +
                ", date='" + date + '\'' +
                "\n\t" + doctor +
                "\n\t" + client +
                "\n\t" + pet +
                "\n\t" + vetService;
    }
}
