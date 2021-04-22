package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Function;

public class Vacancy {

    private static class InstanceSessionFactory {
        public static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getInstance() {
        return InstanceSessionFactory.sessionFactory;
    }

    public <T> T save(T t) {
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        }
        return t;
    }

    private <T> T action(Function<Session, T> function) {
        T result;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            result = function.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    public List<Candidate> getAll() {
        return action(session -> {
            Query<Candidate> query = session.createQuery("from Candidate order by id");
            return query.getResultList();
        });
    }

    public Candidate getById(int id) {
        return action(session -> {
                    Query<Candidate> query = session.createQuery("from Candidate where id =: id");
                    query.setParameter("id", id);
                    return query.getSingleResult();
        });
    }

    public List<Candidate> getByName(String name) {
        return action(session -> {
            Query<Candidate> query = session.createQuery("from Candidate where name =: name");
            query.setParameter("name", name);
            return query.getResultList();
        });
    }

    public boolean update(Candidate candidate) {
        return action(session -> {
            int result;
            Query<Candidate> query = session.createQuery(
                    "update Candidate set name =: name, experience =: experience, salary =: salary where id =: id");
            query.setParameter("name", candidate.getName());
            query.setParameter("experience", candidate.getExperience());
            query.setParameter("salary", candidate.getSalary());
            query.setParameter("id", candidate.getId());
            result = query.executeUpdate();
            return result != 0;
        });
    }

    public boolean delete(int id) {
        int result;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete Candidate where id =: id");
            query.setParameter("id", id);
            result = query.executeUpdate();
            session.getTransaction().commit();
        }
        return result != 0;
    }
}
