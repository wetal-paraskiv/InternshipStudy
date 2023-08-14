package org.example.xml_marshal.xml_entities;

import org.example.models.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "VetRegister")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVetRegister implements Serializable {
    @XmlAttribute
    private Long id;
    @XmlElement
    private String date;
    @XmlElement
    private Doctor doctor;
    @XmlElement
    private Pet pet;
    @XmlElement
    private Client client;
    @XmlElement
    private VetService vetService;

    public XmlVetRegister() {
    }

    public XmlVetRegister(VetRegister vetRegister) {
        this.id = vetRegister.getId();
        this.date = vetRegister.getDate();
        this.doctor = vetRegister.getDoctor();
        this.pet = vetRegister.getPet();
        this.client = vetRegister.getClient();
        this.vetService = vetRegister.getVetService();
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
}
