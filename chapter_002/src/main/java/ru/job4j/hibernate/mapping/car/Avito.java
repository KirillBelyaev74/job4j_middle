package ru.job4j.hibernate.mapping.car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.function.Function;

public class Avito {

    private static class InstanceSessionFactory {
        private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        return InstanceSessionFactory.SESSION_FACTORY;
    }

    public <T> T save(T t) {
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        }
        return t;
    }

    public <T> T getById(Function<Session, T> function) {
        T result;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            result = function.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    public Car getCarById(int id) {
        return getById(
                session -> {
                    Car car;
                    Query query = session.createQuery(
                            "select distinct c from Car c join fetch "
                                    + "c.driver join fetch "
                                    + "c.drivers join fetch "
                                    + "c.engine where c.id =: id");
                    query.setParameter("id", id);
                    car = (Car) query.getSingleResult();
                    return car;
                });
    }

    public Driver getDriverById(int id) {
        return getById(
                session -> {
                    Driver driver;
                    Query query = session.createQuery(
                            "select distinct d from Driver d join fetch d.cars join fetch d.historyCar where d.id =: id");
                    query.setParameter("id", id);
                    driver = (Driver) query.getSingleResult();
                    return driver;
                });
    }
}
