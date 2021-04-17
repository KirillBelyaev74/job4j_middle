package ru.job4j.hibernate.mapping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Dealership {

    private static SessionFactory sessionFactory;

    public Dealership() {
        if (sessionFactory == null) {
            sessionFactory =  new Configuration().configure().buildSessionFactory();
        }
    }

    public CarBrand save(CarBrand car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        }
        return car;
    }
}
