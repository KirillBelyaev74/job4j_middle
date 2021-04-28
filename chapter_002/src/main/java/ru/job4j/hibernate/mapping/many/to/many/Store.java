package ru.job4j.hibernate.mapping.many.to.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Store {

    private static SessionFactory sessionFactory;

    public Store() {
        if (sessionFactory == null) {
            sessionFactory =  new Configuration().configure().buildSessionFactory();
        }
    }

    public Author save(Author author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
        }
        return author;
    }

    public Author get(int id) {
        Author author;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            author = session.get(Author.class, id);
            session.getTransaction().commit();
        }
        return author;
    }

    public void delete(Author author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(author);
            session.getTransaction().commit();
        }
    }
}
