package ru.job4j.hibernate.mapping.car;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "history_owner",
            joinColumns = { @JoinColumn(name = "id_driver")},
            inverseJoinColumns = { @JoinColumn(name = "id_car")})
    private Set<Car> historyCar = new HashSet<>();

    public Driver() {
    }

    public Driver(String name) {
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Set<Car> getHistoryCar() {
        return historyCar;
    }

    public void setHistoryCar(Set<Car> historyCar) {
        this.historyCar = historyCar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return id == driver.id && Objects.equals(name, driver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Driver { "
                + "id = " + id
                + ", name = '" + name + '\''
                + '}';
    }
}
