package org.example.xml_marshal.xml_entities;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "clients")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListXmlClient {
    private List<XmlClient> clientList = new ArrayList<>();

    public List<XmlClient> getClientList() {
        return clientList;
    }
    public void setClientList(List<XmlClient> clientList) {
        this.clientList = clientList;
    }
}
