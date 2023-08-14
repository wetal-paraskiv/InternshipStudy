package org.example.xml_marshal;

import org.example.xml_marshal.xml_entities.*;

import javax.xml.bind.*;
import java.io.*;

public class MarshallingManager {
    public MarshallingManager() throws FileNotFoundException {
    }

    public <T> void marshallList(T t, String path) throws JAXBException, FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream(path);

//        Java API called Java Architecture for XML Binding (JAXB)
        JAXBContext jaxbContext = JAXBContext.newInstance(
                ListXmlCabinetVeterinar.class,
                ListXmlClient.class,
                ListXmlVetRegister.class);

//        Marshalling is the process of converting Java objects into a format that is transferable over the wire.
           Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // default false
        // remove <?xml version="1.0" encoding="UTF-8"?>
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
// change XML encoding
//        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
        try {
            marshaller.marshal(t, fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> T unmarshallList(String path) throws JAXBException {
        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(
                ListXmlCabinetVeterinar.class,
                ListXmlClient.class,
                ListXmlVetRegister.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try {
            T t = (T) unmarshaller.unmarshal(file);
            return t;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public <T> String marshallOne(T t) throws JAXBException {
        // to unmarshall string: T t = JAXB.unmarshal(new StringReader(strObj), T.class);
        // marshall & convert to String one object
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(
                XmlVetRegister.class,
                XmlClient.class,
                XmlCabinetVeterinar.class,
                XmlDoctor.class,
                XmlVetService.class,
                XmlPet.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(t, sw);
            return sw.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public <T> void marshallOne(T t, String fileOutputStream) throws JAXBException {
        // marshall one object and save to file
        File file = new File(fileOutputStream);
        JAXBContext jaxbContext = JAXBContext.newInstance(
                XmlVetRegister.class,
                XmlClient.class,
                XmlCabinetVeterinar.class,
                XmlDoctor.class,
                XmlVetService.class,
                XmlPet.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(t, file);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> T unmarshallOne(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(
                XmlVetRegister.class,
                XmlClient.class,
                XmlCabinetVeterinar.class,
                XmlDoctor.class,
                XmlVetService.class,
                XmlPet.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try {
            T t = (T) unmarshaller.unmarshal(new File(path));

            return t;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
