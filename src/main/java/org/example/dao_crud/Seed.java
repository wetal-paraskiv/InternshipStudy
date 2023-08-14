package org.example.dao_crud;

import org.example.models.*;

import java.util.Arrays;
import java.util.List;

public class Seed {
    public void run() {

        DaoCabinetVeterinar daoCabinetVeterinar = new DaoCabinetVeterinar();
        DaoClient daoClient = new DaoClient();
        DaoPet daoPet = new DaoPet();

        // creating cabinet veterinar
        CabinetVeterinar cv = new CabinetVeterinar(
                "I love my PET",
                "Chisinau bul. Moscova 12",
                "+373********",
                "ilovemypet@gmail.com ");

        // creating doctors & adding to cabinet
        Doctor d1 = new Doctor("Ana", "Mutates", "anamusteata@gmail.com", "+373794*****");
        Doctor d2 = new Doctor("Sorina", "Post ica", "sorinapostica@gmail.com", "+373794*****");
        cv.getDoctors().add(d1);
        cv.getDoctors().add(d2);

        // creating vatServices & adding to cabinet
        List<VetService> vetServiceList = Arrays.asList(
                new VetService("Consulting first time", 0),
                new VetService("Consulting Monthly Subscription", 10),
                new VetService("Consulting Year Subscription", 5),
                new VetService("First Aid", 0),
                new VetService("Aid Monthly Subscription", 15),
                new VetService("Aid Year Subscription", 7.5),
                new VetService("Medicine 1", 5),
                new VetService("Medicine 2", 10),
                new VetService("Medicine 3", 15));

        cv.getVetServices().addAll(vetServiceList);

        // saving cabinet after adding doctors, services
        daoCabinetVeterinar.save(cv);


        // creating clients, pets adding to clients & saving client
        Client c1 = new Client("Sorin", "RadScheduler", "sorinradulescu@gmail.com", "+373794*****");
        daoClient.save(c1);
        Pet p1 = new Pet("Jake", "Dog");
        Pet p2 = new Pet("Alta", "Cat");
        p1.setClient(c1);
        p2.setClient(c1);
        daoPet.save(p1);
        daoPet.save(p2);

        Client c2 = new Client("Constanta", "Apalachicola", "apalachicola@gmail.com", "+373794*****");
        daoClient.save(c2);
        Pet p3 = new Pet("Sky", "Parrot");
        p3.setClient(c2);
        Pet p4 = new Pet("Kitty", "Tiger");
        p4.setClient(c2);
        daoPet.save(p3);
        daoPet.save(p4);

        Client c3 = new Client("Beatrice", "Chicanos", "beatricechicaros@gmail.com", "+373794*****");
        daoClient.save(c3);
        Pet p5 = new Pet("Luna", "Parrot");
        p5.setClient(c3);
        daoPet.save(p5);


        // register
        VetRegister[] registers = new VetRegister[]{
                new VetRegister(d1, p1, vetServiceList.get(0)),
                new VetRegister(d1, p3, vetServiceList.get(1)),
                new VetRegister(d1, p1, vetServiceList.get(2)),
                new VetRegister(d1, p2, vetServiceList.get(3)),
                new VetRegister(d1, p4, vetServiceList.get(4)),
                new VetRegister(d2, p1, vetServiceList.get(5)),
                new VetRegister(d2, p1, vetServiceList.get(6)),
                new VetRegister(d2, p3, vetServiceList.get(7)),
                new VetRegister(d2, p4, vetServiceList.get(8)),
                new VetRegister(d2, p2, vetServiceList.get(8)),
        };
        DaoVetRegister daoVetRegister = new DaoVetRegister();
        Arrays.stream(registers).forEach(daoVetRegister::save);


    }
}
