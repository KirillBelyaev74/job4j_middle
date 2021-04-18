package ru.job4j.hibernate.mapping.oneToMany;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "carModelList")
    private CarBrand carBrand;

    public CarModel() {
    }

    public CarModel(String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return id == carModel.id && Objects.equals(name, carModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CarModel { " + "id = " + id
                + ", name = '" + name + '\'' + '}';
    }
}
