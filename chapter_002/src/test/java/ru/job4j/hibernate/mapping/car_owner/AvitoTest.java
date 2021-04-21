package ru.job4j.hibernate.mapping.car_owner;

import org.junit.Test;

import java.util.Set;

public class AvitoTest {

    @Test
    public void start() {
        Avito avito = new Avito();

        Car audi_a5 = new Car("Audi A5");
        Car audi_a7 = new Car("Audi A7");

        Engine two = new Engine("2.0");

        Driver kirill = new Driver("Kirill");
        Driver kostya = new Driver("Kostya");

        avito.save(two);

        avito.save(kirill);
        avito.save(kostya);

        audi_a5.setEngine(two);
        audi_a5.setDriver(kirill);
        audi_a5.setDrivers(Set.of(kostya, kirill));
        avito.save(audi_a5);

        audi_a7.setEngine(two);
        audi_a7.setDriver(kostya);
        audi_a7.setDrivers(Set.of(kirill, kostya));
        avito.save(audi_a7);

        System.out.println(audi_a5.getEngine());
        System.out.println(audi_a5.getDriver());
        System.out.println(audi_a5.getDrivers());

        System.out.println(kirill.getCars());
        System.out.println(kirill.getHistoryCar());
    }
}