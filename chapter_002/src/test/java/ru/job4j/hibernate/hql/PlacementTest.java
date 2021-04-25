package ru.job4j.hibernate.hql;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PlacementTest {

    private final Placement placement = new Placement();

    @Test
    public void whenSave() {
        DatabaseVacancy one = new DatabaseVacancy("HH.Ru");
        DatabaseVacancy two = new DatabaseVacancy("Zarplata.Ru");
        placement.save(one);
        placement.save(two);
        Candidate candidate1 = new Candidate("Kirill", 0, 100);
        Candidate candidate2 = new Candidate("Kostya", 2, 200);
        Candidate candidate3 = new Candidate("Ivan", 5, 500);
        candidate1.setDatabaseVacancy(one);
        candidate2.setDatabaseVacancy(one);
        candidate3.setDatabaseVacancy(two);
        placement.save(candidate1);
        placement.save(candidate2);
        placement.save(candidate3);
        Vacancy vacancy1 = new Vacancy("OZON");
        Vacancy vacancy2 = new Vacancy("Wallberis");
        Vacancy vacancy3 = new Vacancy("Avito");
        Vacancy vacancy4 = new Vacancy("Auto");
        vacancy3.setDatabaseVacancy(two);
        vacancy2.setDatabaseVacancy(one);
        vacancy1.setDatabaseVacancy(one);
        vacancy4.setDatabaseVacancy(two);
        placement.save(vacancy1);
        placement.save(vacancy2);
        placement.save(vacancy3);
        placement.save(vacancy4);
    }

    @Test
    public void whenGetCandidateById() {
        Candidate candidate = placement.getByIdCandidate(1);
        assertThat(candidate.getName(), is("Kirill"));
    }

    @Test
    public void whenGetCandidateByName() {
        List<Candidate> candidates = placement.getByNameCandidate("Kostya");
        assertThat(candidates.get(0).getName(), is("Kostya"));
    }

    @Test
    public void whenGetAllCandidate() {
        List<Candidate> candidates = placement.getAllCandidate();
        assertThat(candidates.get(0).getName(), is("Kirill"));
        assertThat(candidates.get(1).getName(), is("Kostya"));
        assertThat(candidates.get(2).getName(), is("Ivan"));
    }

    @Test
    public void whenDeleteCandidateById() {
        boolean result = placement.deleteCandidate(3);
        assertTrue(result);
    }

    @Test
    public void whenUpdateCandidateById() {
        Candidate misha = new Candidate("Misha", 7, 700);
        Candidate dima = new Candidate("Dima", 8, 800);

        placement.save(misha);
        dima.setId(misha.getId());

        boolean result = placement.updateCandidate(dima);
        Candidate candidateResult = placement.getByIdCandidate(dima.getId());

        assertTrue(result);
        assertThat(candidateResult.getName(), is("Dima"));
    }

    @Test
    public void whenGetDatabaseVacancyById() {
        DatabaseVacancy result = placement.getByIdDatabaseVacancy(2);
        System.out.println(result.getCandidate());
        System.out.println(result.getVacancies());
    }

    @Test
    public void whenGetVacancyById() {
        Vacancy result = placement.getByIdVacancy(4);
        System.out.println(result.getDatabaseVacancy());
    }
}