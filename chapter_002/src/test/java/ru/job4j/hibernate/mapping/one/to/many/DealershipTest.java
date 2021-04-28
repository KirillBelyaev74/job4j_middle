package ru.job4j.hibernate.mapping.one.to.many;

import org.junit.Test;

public class DealershipTest {

    @Test
    public void whenAddCarThenGetTheCarById() {
        Dealership dealership = new Dealership();

        CarBrand carBrand = new CarBrand("Audi");
        carBrand.getCarModelList().add(new CarModel("A5"));
        carBrand.getCarModelList().add(new CarModel("A6"));
        carBrand.getCarModelList().add(new CarModel("A7"));
        carBrand.getCarModelList().add(new CarModel("A8"));

        dealership.save(carBrand);
        carBrand = dealership.getById(carBrand.getId());
        System.out.println(carBrand.getCarModelList());
    }
}
