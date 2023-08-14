package org.example;

import org.example.dao_crud.*;
import org.example.xml_marshal.MarshallingManager;
import org.example.xml_marshal.xml_entities.*;
import org.example.models.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {

    MarshallingManager marshallingManager = new MarshallingManager();
    DaoVetRegister daoVetRegister = new DaoVetRegister();
    DaoClient daoClient = new DaoClient();
    DaoCabinetVeterinar daoCabinetVeterinar = new DaoCabinetVeterinar();

    public DataManager() throws FileNotFoundException {
    }

    public void importCabinetToXML() throws JAXBException, FileNotFoundException {

        // getting list of objects from oracle database
        List<CabinetVeterinar> cabinetVeterinarList = new DaoCabinetVeterinar().getAll();

        // casting each object to Xml Classes
        List<XmlCabinetVeterinar> xmlCabinetVeterinarList =
                cabinetVeterinarList.stream().map(XmlCabinetVeterinar::new).collect(Collectors.toList());

        // instantiating ListXmlObject for marshalling all list at once
        ListXmlCabinetVeterinar cabinetsObjList = new ListXmlCabinetVeterinar();
        cabinetsObjList.setCabinets(xmlCabinetVeterinarList);

        // marshalling all list to file
        String cabinetsPath = "src/main/resources/marshaled_data/cabinets.xml";
        marshallingManager.marshallList(cabinetsObjList, cabinetsPath);
    }

    public void importClientToXML() throws JAXBException, FileNotFoundException {
        // getting list of objects from oracle database
        List<Client> clientList = new DaoClient().getAll();

        // casting each object to Xml Classes
        List<XmlClient> xmlClients = clientList.stream().map(XmlClient::new).collect(Collectors.toList());

        // instantiating ListXmlObject for marshalling all list at once
        ListXmlClient clientsObjList = new ListXmlClient();
        clientsObjList.setClientList(xmlClients);

        // marshalling all list to file
        String clientsPath = "src/main/resources/marshaled_data/clients.xml";
        marshallingManager.marshallList(clientsObjList, clientsPath);
    }

    public void importRegisterToXml() throws JAXBException, FileNotFoundException {

        // getting list of objects from oracle database
        List<VetRegister> registerList = new DaoVetRegister().getAll();

        // casting each object to Xml
        List<XmlVetRegister> xmlVetRegisterList = registerList.stream()
                .map(XmlVetRegister::new)
                .collect(Collectors.toList());

        // instantiating registerObjList for marshalling list at once
        ListXmlVetRegister registerObjList = new ListXmlVetRegister();
        registerObjList.setRegisters(xmlVetRegisterList);

        // marshalling all list to file
        String registerPath = "src/main/resources/marshaled_data/registers.xml";
        marshallingManager.marshallList(registerObjList, registerPath);
    }

    public void exportCabinetToDB(String sourcePath) throws JAXBException {
        // getting all cabinets in an object
        ListXmlCabinetVeterinar listOfObjects = marshallingManager.unmarshallList(sourcePath);
        // update cabinets
        listOfObjects.getCabinets().forEach(xmlCv -> daoCabinetVeterinar.update(xmlCv));
    }

    public void exportRegisterToDB(String sourcePath) throws JAXBException {
        ListXmlVetRegister listOfObjects = marshallingManager.unmarshallList(sourcePath);
        listOfObjects.getRegisters().forEach(xmlVetRegister -> daoVetRegister.update(xmlVetRegister));
    }

    public void exportClientPetsToDB(String sourcePath) throws JAXBException {
        ListXmlClient listOfObjects = marshallingManager.unmarshallList(sourcePath);
        listOfObjects.getClientList().forEach(xmlClient -> daoClient.update(xmlClient));
    }

}
