package ru.job4j.hibernate.hql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PlacementTest {

    private final Placement placement = new Placement();

    private DatabaseVacancy one = new DatabaseVacancy("HH.Ru");
    private DatabaseVacancy two = new DatabaseVacancy("Zarplata.Ru");
    private Candidate kirill = new Candidate("Kirill", 0, 100);
    private Candidate kostya = new Candidate("Kostya", 2, 200);
    private Vacancy ozon = new Vacancy("OZON");
    private Vacancy wallberis = new Vacancy("Wallberis");

    @Before
    public void start() {
        placement.save(one);
        placement.save(two);
        kirill.setDatabaseVacancy(one);
        kostya.setDatabaseVacancy(one);
        placement.save(kirill);
        placement.save(kostya);
        wallberis.setDatabaseVacancy(one);
        ozon.setDatabaseVacancy(two);
        placement.save(ozon);
        placement.save(wallberis);
    }

    @After
    public void finish() {
        placement.action(session -> {
            session.createQuery("delete from Vacancy").executeUpdate();
            session.createQuery("delete from Candidate").executeUpdate();
            session.createQuery("delete from DatabaseVacancy").executeUpdate();
            return null;
        });
    }

    @Test
    public void whenGetCandidateById() {
        Candidate candidate = placement.getByIdCandidate(kirill.getId());
        assertThat(candidate, is(kirill));
    }

    @Test
    public void whenGetCandidateByName() {
        List<Candidate> candidates = placement.getByNameCandidate("Kostya");
        assertThat(candidates.get(0), is(kostya));
    }

    @Test
    public void whenGetAllCandidate() {
        List<Candidate> candidates = placement.getAllCandidate();
        assertThat(candidates.size(), is(2));
    }

    @Test
    public void whenDeleteCandidateById() {
        boolean result = placement.deleteCandidate(kirill.getId());
        assertTrue(result);
    }

    @Test
    public void whenUpdateCandidateById() {
        Candidate misha = new Candidate("Misha", 7, 700);
        misha.setId(kostya.getId());

        boolean result = placement.updateCandidate(misha);
        Candidate candidateResult = placement.getByIdCandidate(misha.getId());

        assertTrue(result);
        assertThat(candidateResult, is(misha));
    }

    @Test
    public void whenGetDatabaseVacancyById() {
        DatabaseVacancy result = placement.getByIdDatabaseVacancy(one.getId());
        assertThat(result, is(one));
    }

    @Test
    public void whenGetVacancyById() {
        Vacancy result = placement.getByIdVacancy(ozon.getId());
        assertThat(result.getDescription(), is(ozon.getDescription()));
    }
}