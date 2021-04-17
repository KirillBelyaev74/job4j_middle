package ru.job4j.hibernate.mapping.oneToMany;

import org.junit.Test;
import ru.job4j.hibernate.mapping.oneToMany.CarBrand;
import ru.job4j.hibernate.mapping.oneToMany.CarModel;
import ru.job4j.hibernate.mapping.oneToMany.Dealership;

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
    }
}
