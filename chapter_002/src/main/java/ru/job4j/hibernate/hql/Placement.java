package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Function;

public class Placement {

    private static class InstanceSessionFactory {
        public static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    private SessionFactory getInstance() {
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

    public <T> T action(Function<Session, T> function) {
        T result;
        try (Session session = getInstance().openSession()) {
            session.beginTransaction();
            result = function.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    public List<Candidate> getAllCandidate() {
        return action(session -> {
            Query<Candidate> query = session.createQuery(
                    "select distinct c from Candidate as c join fetch c.databaseVacancy order by c.id");
            return query.getResultList();
        });
    }

    public Candidate getByIdCandidate(int id) {
        return action(session -> {
            Query<Candidate> query = session.createQuery(
                    "select distinct c from Candidate as c join fetch c.databaseVacancy where c.id =: id");
            query.setParameter("id", id);
            return query.getSingleResult();
        });
    }

    public List<Candidate> getByNameCandidate(String name) {
        return action(session -> {
            Query<Candidate> query = session.createQuery(
                    "select distinct c from Candidate as c join fetch c.databaseVacancy where c.name =: name");
            query.setParameter("name", name);
            return query.getResultList();
        });
    }

    public boolean updateCandidate(Candidate candidate) {
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

    public boolean deleteCandidate(int id) {
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

    public List<DatabaseVacancy> getAllDatabaseVacancy() {
        return action(session -> {
            Query<DatabaseVacancy> query = session.createQuery(
                    "select distinct d from DatabaseVacancy as d join fetch d.candidate join fetch d.vacancies order by d.id");
            return query.getResultList();
        });
    }

    public DatabaseVacancy getByIdDatabaseVacancy(int id) {
        return action(session -> {
            Query<DatabaseVacancy> query = session.createQuery(
                    "select distinct d from DatabaseVacancy as d join fetch d.candidate join fetch d.vacancies where d.id =: id");
            query.setParameter("id", id);
            return query.getSingleResult();
        });
    }

    public List<Vacancy> getAllVacancy() {
        return action(session -> {
            Query<Vacancy> query = session.createQuery(
                    "select distinct v from Vacancy as v join fetch v.databaseVacancy order by v.id");
            return query.getResultList();
        });
    }

    public Vacancy getByIdVacancy(int id) {
        return action(session -> {
            Query<Vacancy> query = session.createQuery(
                    "select distinct v from Vacancy as v join fetch v.databaseVacancy where v.id =: id");
            query.setParameter("id", id);
            return query.getSingleResult();
        });
    }
}
