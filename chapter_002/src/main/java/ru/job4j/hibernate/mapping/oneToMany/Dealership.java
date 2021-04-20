package ru.job4j.hibernate.mapping.oneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public CarBrand getById(int id) {
        CarBrand carBrand;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select distinct b from CarBrand b join fetch b.carModelList where b.id =: id");
            carBrand = (CarBrand) query.setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
        }
        return carBrand;
    }
}
