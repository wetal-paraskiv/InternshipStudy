package org.example.xml_marshal.xml_entities;

import org.example.models.CabinetVeterinar;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "cabinetVeterinar")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CabinetVeterinar", propOrder = {
        "id",
        "name",
        "address",
        "email",
        "phone",
        "doctors",
        "vetServices"
})
public class XmlCabinetVeterinar {

    @XmlAttribute
    private Long id;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Address")
    private String address;
    @XmlElement(name = "Email")
    private String email;
    @XmlElement(name = "Phone")
    private String phone;
    //@XmlTransient // Prevents the mapping
    @XmlElement(name = "Cabinet_Doctors")
    private Set<XmlDoctor> doctors = new HashSet<>();
    @XmlElement(name = "Cabinet_Services")
    private Set<XmlVetService> vetServices = new HashSet<>();

    public XmlCabinetVeterinar() {
    }

    public XmlCabinetVeterinar(CabinetVeterinar cv) {
        this.id = cv.getId();
        this.name = cv.getName();
        this.address = cv.getAddress();
        this.email = cv.getEmail();
        this.phone = cv.getPhone();
        this.doctors = cv.getDoctors().stream().map(XmlDoctor::new).collect(Collectors.toSet());
        this.vetServices = cv.getVetServices().stream().map(vs -> new XmlVetService(vs)).collect(Collectors.toSet());
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

    public Set<XmlDoctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<XmlDoctor> doctors) {
        this.doctors = doctors;
    }

    public Set<XmlVetService> getVetServices() {
        return vetServices;
    }

    public void setVetServices(Set<XmlVetService> vetServices) {
        this.vetServices = vetServices;
    }
}
