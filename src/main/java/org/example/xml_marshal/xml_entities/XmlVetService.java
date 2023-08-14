package org.example.xml_marshal.xml_entities;

import org.example.models.VetService;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "VetService")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVetService {

    @XmlAttribute
    private Long id;
    @XmlElement
    private String name;
    @XmlElement
    private double price;

    public XmlVetService() {
    }

    public XmlVetService(VetService vetService) {
        this.id = vetService.getId();
        this.name = vetService.getName();
        this.price = vetService.getPrice();
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
}
