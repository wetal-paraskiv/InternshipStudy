package org.example.models;

import jakarta.persistence.*;


@Entity
@Table(name = "PETS")
public class Pet {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ANIMAL_TYPE")
    private String animalType;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;

    public Pet() {
    }

    public Pet(String name, String animalType) {
        this.name = name;
        this.animalType = animalType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Pet: " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animalType='" + animalType + '\'' +
                '}';
    }

}
