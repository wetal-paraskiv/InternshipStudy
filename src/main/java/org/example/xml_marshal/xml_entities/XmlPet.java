package org.example.xml_marshal.xml_entities;

import org.example.models.Pet;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "pet")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPet {
    @XmlAttribute
    private Long id;
    @XmlElement
    private String name;
    @XmlElement
    private String animalType;

    @XmlElement
    private XmlClient xmlClient;

    public XmlPet() {
    }

    public XmlPet(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.animalType = pet.getAnimalType();
        this.xmlClient = new XmlClient(pet.getClient());
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

    public XmlClient getXmlClient() {
        return xmlClient;
    }

    public void setXmlClient(XmlClient xmlClient) {
        this.xmlClient = xmlClient;
    }
}
