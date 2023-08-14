package org.example.xml_marshal.xml_entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListXmlCabinetVeterinar", propOrder = {"cabinets"})
@XmlRootElement(name = "cabinets")
public class ListXmlCabinetVeterinar {

    private List<XmlCabinetVeterinar> cabinets = new ArrayList<>();

    public List<XmlCabinetVeterinar> getCabinets() {
        return cabinets;
    }

    public void setCabinets(List<XmlCabinetVeterinar> cabinets) {
        this.cabinets = cabinets;
    }
}
