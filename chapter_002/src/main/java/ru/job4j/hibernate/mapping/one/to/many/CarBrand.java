package ru.job4j.hibernate.mapping.one.to.many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_brand")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<CarModel> carModelList = new ArrayList<>();

    public CarBrand() {
    }

    public CarBrand(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getCarModelList() {
        return carModelList;
    }

    public List<CarModel> setCarModelList() {
        return carModelList;
    }

    public void addCarModel(CarModel carModel) {
        carModelList.add(carModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return id == carBrand.id && Objects.equals(name, carBrand.name)
                && Objects.equals(carModelList, carBrand.carModelList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carModelList);
    }

    @Override
    public String toString() {
        return "CarBrand {"
                + "id = " + id
                + ", name = '" + name + '\''
                + ", carModelList = " + carModelList
                + '}';
    }
}
