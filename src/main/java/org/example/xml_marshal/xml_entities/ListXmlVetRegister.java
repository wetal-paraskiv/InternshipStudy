package org.example.xml_marshal.xml_entities;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "registers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListXmlVetRegister {

    private List<XmlVetRegister> registers = new ArrayList<>();

    public List<XmlVetRegister> getRegisters() {
        return registers;
    }

    public void setRegisters(List<XmlVetRegister> registers) {
        this.registers = registers;
    }
}
