package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "VET_SERVICE")
public class VetService {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private double price;


    public VetService() {
    }

    public VetService(String name, double price) {
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VetService: " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price;
    }
}
