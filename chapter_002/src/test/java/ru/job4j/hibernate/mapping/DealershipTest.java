package ru.job4j.hibernate.mapping;

import org.junit.Test;

public class DealershipTest {

    @Test
    public void whenAddCarThenGetTheCarById() {
        Dealership dealership = new Dealership();
        CarBrand carBrand = new CarBrand("Audi");
        carBrand.addCarModel(new CarModel("A5"));
        carBrand.addCarModel(new CarModel("A6"));
        carBrand.addCarModel(new CarModel("A7"));
        carBrand.addCarModel(new CarModel("A8"));
        dealership.save(carBrand);
    }
}
