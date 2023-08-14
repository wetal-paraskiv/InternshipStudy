package org.example;


import org.example.activemq.Producer;
import org.example.dao_crud.*;
import org.example.models.*;
import org.example.xml_marshal.MarshallingManager;
import org.example.xml_marshal.xml_entities.XmlCabinetVeterinar;
import org.example.xml_marshal.xml_entities.XmlClient;
import org.example.xml_marshal.xml_entities.XmlVetRegister;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class App {

    public static void main(String[] args) throws FileNotFoundException, JAXBException {
        new Seed().run();

//        DaoPet daoPet = new DaoPet();
//        Pet p = daoPet.findByName("Alta");
//        System.out.println(p);

        MarshallingManager marshManager = new MarshallingManager();
        DataManager dataManager = new DataManager();
        DaoCabinetVeterinar daoCabinetVeterinar = new DaoCabinetVeterinar();
        DaoClient daoClient = new DaoClient();
        DaoVetRegister daoVetRegister = new DaoVetRegister();
        Scanner scanner = new Scanner(System.in);
        boolean activeApp = true;

//        List<VetRegister> doctor1Registers = daoVetRegister.filterRegistersByDoctorId(1l);
//        for (VetRegister vr: doctor1Registers) {
//            System.out.println(vr);
//        }

        System.out.println("Welcome to Veterinary Cabinet!");

        while (activeApp) {
            System.out.println("Press\n(1) : XML import/export | (2) Data Display | (3) CRUD  | (q) for quit.");
            String input = scanner.next().toLowerCase();

            switch (input) {

                case "q":
                    activeApp = false;
                    System.out.println("Quitting application.. :(");
                    break;

                case "1":
                    System.out.println("(1) import from DB to XML  (2) export XML to DB");
                    String imp_exp = scanner.next();

                    switch (imp_exp) {

                        case "1": {
                            // importing database to XML
                            dataManager.importCabinetToXML();
                            dataManager.importClientToXML();
                            dataManager.importRegisterToXml();
                            break;
                        }

                        case "2": {
                            // after data changes in XML -> exporting XML to Database
                            dataManager.exportCabinetToDB("src/main/resources/marshaled_data/cabinets.xml");
                            dataManager.exportClientPetsToDB("src/main/resources/marshaled_data/clients.xml");
                            dataManager.exportRegisterToDB("src/main/resources/marshaled_data/registers.xml");
                            break;
                        }
                    }
                    break;

                case "2":
                    System.out.println("You chose to display Data :)");
                    System.out.println("(1) cabinet info  |  (2) clients info  |  (3) registers info");
                    String chooseDataInfo = scanner.next();

                    switch (chooseDataInfo) {
                        case "1": {
                            System.out.println(daoCabinetVeterinar.get(1).orElse(null));
                            break;
                        }
                        case "2": {
                            daoClient.getAll().forEach(System.out::println);
                            break;
                        }
                        case "3": {
                            daoVetRegister.getAll().forEach(System.out::println);
                            break;
                        }
                    }
                    break;

                case "3":
                    System.out.println("You chose CRUD by ID :)");

                    System.out.println("(1) cabinet  |  (2) clients  |  (3) registers");
                    String dataChoice = scanner.next();

                    switch (dataChoice) {
                        case "1": {
                            // dealing with cabinet data
                            System.out.println("Ok, dealing with Cabinets!");
                            System.out.println("(1) Create  |  (2) Read  |  (3) Update  |  (4) Delete  |  (5) JMS XML");
                            String crudChoice = scanner.next();

                            switch (crudChoice) {
                                case "1": {
                                    CabinetVeterinar cabinet_veterinar = daoCabinetVeterinar.newData();
                                    daoCabinetVeterinar.save(cabinet_veterinar);
                                    System.out.println("Creating of new cabinet was successfully! :)");
                                    break;
                                }
                                case "2": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    if (isNumeric(id)) {
                                        CabinetVeterinar getByIdCabinet = daoCabinetVeterinar.getById(id);
                                        try {
                                            if (!getByIdCabinet.equals(null)) {
                                                System.out.println("Object get by ID: successfully! :)");
                                                System.out.println(daoCabinetVeterinar.get(Long.parseLong(id)));
                                                break;
                                            }
                                        } catch (NullPointerException e) {
                                            System.out.println("Error 404, not found... :(");
                                            break;
                                        }
                                    } else {
                                        System.out.println("Error 404, not found... :(");
                                    }
                                    break;
                                }
                                case "3": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    if (isNumeric(id)) {
                                        CabinetVeterinar getByIdCabinet = daoCabinetVeterinar.getById(id);
                                        try {
                                            if (!getByIdCabinet.equals(null)) {
                                                CabinetVeterinar updateCabinet = daoCabinetVeterinar.newData();
                                                updateCabinet.setId(Long.valueOf(id));
                                                daoCabinetVeterinar.update(updateCabinet);
                                                System.out.println("Updated Cabinet by ID: successfully! :)");
                                                System.out.println(daoCabinetVeterinar.get(Long.parseLong(id)));
                                                break;
                                            }
                                        } catch (NullPointerException e) {
                                            System.out.println("Error 404, not found... :(");
                                            break;
                                        }
                                    } else {
                                        System.out.println("Error 404, not found... :(");
                                    }
                                    break;
                                }
                                case "5": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    CabinetVeterinar cv =
                                            (CabinetVeterinar) daoCabinetVeterinar.get(Long.parseLong(id)).orElse(null);
                                    // marshall the object & convert to string
                                    String strObj = marshManager.marshallOne(new XmlCabinetVeterinar(cv));
                                    // produce string object to Queue
                                    Producer producer = new Producer(strObj, "cabinetVet");
                                    Thread producerThread = new Thread(producer);
                                    producerThread.start();
                                    break;
                                }
                            }
                        }
                        break;
                        case "2": {
                            // dealing with Client data
                            System.out.println("Ok, dealing with Clients!");
                            System.out.println("(1) Create  |  (2) Read  |  (3) Update  |  (4) Delete  |  (5) JMS XML");
                            String crudChoice = scanner.next();

                            switch (crudChoice) {
                                case "1": {
                                    Client client = daoClient.newData();
                                    daoClient.save(client);
                                    System.out.println("Creating of new client was successfully! :)");
                                    break;
                                }
                                case "2": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    if (isNumeric(id)) {
                                        Client getByIdClient = daoClient.getById(id);
                                        try {
                                            if (!getByIdClient.equals(null)) {
                                                System.out.println("Object get by ID: successfully! :)");
                                                System.out.println(daoClient.get(Long.parseLong(id)));
                                                break;
                                            }
                                        } catch (NullPointerException e) {
                                            System.out.println("Error 404, not found... :(");
                                            break;
                                        }
                                    } else {
                                        System.out.println("Error 404, not found... :(");
                                    }
                                    break;

                                }
                                case "3": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    if (isNumeric(id)) {
                                        Client getByIdClient = daoClient.getById(id);
                                        try {
                                            if (!getByIdClient.equals(null)) {
                                                Client updateClient = daoClient.newData();
                                                updateClient.setId(Long.valueOf(id));
                                                daoClient.update(updateClient);
                                                System.out.println("Updated Client by ID: successfully! :)");
                                                System.out.println(daoClient.get(Long.parseLong(id)));
                                                break;
                                            }
                                        } catch (NullPointerException e) {
                                            System.out.println("Error 404, not found... :(");
                                            break;
                                        }
                                    } else {
                                        System.out.println("Error 404, not found... :(");
                                    }
                                    break;
                                }
                                case "4": {
                                    System.out.println("Insert valid ID or UserName");
                                    String idOrName = scanner.next();

                                    // check whether was passed id or userName
                                    if (isNumeric(idOrName)) {
                                        Client c = daoClient.getById(idOrName);
                                        if (null != c) {
                                            // check for constraints first
                                            if (daoClient.constraintCheck(Long.parseLong(idOrName))) {
                                                daoClient.delete(c);
                                                System.out.println("Deleted object successfully! :)-----");
                                            } else {
                                                System.out.println("That object has records, you can not delete it! :(");
                                            }
                                        } else {
                                            System.out.println("Error 404, not found... :(");
                                            break;
                                        }
                                    } else { // dealing with username
                                        List<Client> list = daoClient.getAll().stream().filter(client ->
                                                        (client.getFirstName().toLowerCase()).contains(idOrName.toLowerCase()))
                                                .collect(Collectors.toList());
                                        if (list.size() > 0) {
                                            Client c = list.get(0);
                                            // check for constraints first
                                            if (daoClient.constraintCheck(Long.parseLong(idOrName))) {
                                                daoClient.delete(c);
                                            }
                                            System.out.println("Deleted object by name successfully! :)");
                                        } else {
                                            System.out.println("Error 404, didn't find such name... :(");
                                            break;
                                        }
                                    }
                                    break;
                                }
                                case "5": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    Client client = (Client) daoClient.get(Long.parseLong(id)).orElse(null);
                                    // marshall the object & convert to string
                                    String strObj = marshManager.marshallOne(new XmlClient(client));
                                    // produce xml object to Queue
                                    Producer producer = new Producer(strObj, "client");
                                    Thread producerThread = new Thread(producer);
                                    producerThread.start();
                                    break;
                                }

                            }
                        }
                        break;
                        case "3": {
                            // dealing with VetRegister data
                            System.out.println("Ok, dealing with vet Registers!");
                            System.out.println("(1) Create  |  (2) Read  |  (3) Update  |  (4) Delete  |  " +
                                    "(5) JMS XML  |  (6) RegDatePetWhereDoctorNameLikeClientName  ");
                            String crudChoice = scanner.next();

                            switch (crudChoice) {
                                case "1": {
                                    VetRegister vetRegister = daoVetRegister.newData();
                                    daoVetRegister.save(vetRegister);
                                    System.out.println("Creating of new register was successfully! :)");
                                    break;
                                }
                                case "2": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    VetRegister vetRegister = (VetRegister) daoVetRegister.get(Long.parseLong(id)).orElse(null);
                                    System.out.println(vetRegister);
                                    break;
                                }
                                case "3": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    VetRegister vetRegister = daoVetRegister.newData();
                                    vetRegister.setId(Long.valueOf(id));
                                    daoVetRegister.update(vetRegister);
                                    System.out.println("Updated object successfully! :)");
                                    break;
                                }
                                case "4": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    VetRegister vr = (VetRegister) daoVetRegister.get(Long.parseLong(id)).orElse(null);
                                    daoVetRegister.delete(vr);
                                    System.out.println("Deleted object successfully! :)");
                                    break;
                                }
                                case "5": {
                                    System.out.println("Insert valid ID");
                                    String id = scanner.next();
                                    VetRegister vetRegister = (VetRegister) daoVetRegister.get(Long.parseLong(id)).orElse(null);
                                    // marshall the object & convert to string
                                    String strObj = marshManager.marshallOne(new XmlVetRegister(vetRegister));
                                    // produce xml object to Queue
                                    Producer producer = new Producer(strObj, "vetRegister");
                                    Thread producerThread = new Thread(producer);
                                    producerThread.start();
                                    break;
                                }
                                case "6": {
                                    System.out.println("Insert name like to look for...");
                                    String nameLike = scanner.next();
                                    System.out.println("\tRegisters Where DoctorName & ClientName LIKE: " + nameLike);
                                    System.out.println("\tfound: " +
                                            daoVetRegister.registersWhereDoctorNameLikeClientName(nameLike).size() +
                                            " registers.");
                                    daoVetRegister
                                            .registersWhereDoctorNameLikeClientName(nameLike)
                                            .forEach(System.out::println);
                                    break;
                                }
                            }
                        }
                    }
                    break;

            }

        }

    }

    public static boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
