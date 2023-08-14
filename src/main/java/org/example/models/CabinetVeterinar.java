package org.example.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "CABINET_VETERINAR")
public class CabinetVeterinar {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "cabinet") // cabinet is the name for column that is created in doctors table
    private Set<Doctor> doctors = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="cabinet")
    private Set<VetService> vetServices = new HashSet<>();


    public CabinetVeterinar() {
    }

    public CabinetVeterinar(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<VetService> getVetServices() {
        return vetServices;
    }

    public void setVetServices(Set<VetService> vetServices) {
        this.vetServices = vetServices;
    }

    @Override
    public String toString() {
        return "CabinetVeterinar\n" +
                "\tid=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' + "\n" +
                "doctors\n\t" + doctors + "\n" +
                "vetServices\n\t" + vetServices +
                '}';
    }
}
