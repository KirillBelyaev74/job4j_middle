package ru.job4j.hibernate.hql;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class VacancyTest {

    private final Vacancy vacancy = new Vacancy();

    @Test
    public void whenSaveCandidate() {
        vacancy.save(new Candidate("Kirill", 0, 100));
        vacancy.save(new Candidate("Kostya", 2, 200));
        vacancy.save(new Candidate("Ivan", 5, 500));
    }

    @Test
    public void whenGetCandidateById() {
        Candidate candidate = vacancy.getById(1);
        assertThat(candidate.getName(), is("Kirill"));
    }

    @Test
    public void whenGetCandidateByName() {
        List<Candidate> candidates = vacancy.getByName("Kostya");
        assertThat(candidates.get(0).getName(), is("Kostya"));
    }

    @Test
    public void whenGetAllCandidate() {
        List<Candidate> candidates = vacancy.getAll();
        assertThat(candidates.get(0).getName(), is("Kirill"));
        assertThat(candidates.get(1).getName(), is("Kostya"));
        assertThat(candidates.get(2).getName(), is("Ivan"));
    }

    @Test
    public void whenDeleteCandidateById() {
        boolean result = vacancy.delete(3);
        assertTrue(result);
    }

    @Test
    public void whenUpdateCandidateById() {
        Candidate misha = new Candidate("Misha", 7, 700);
        Candidate dima = new Candidate("Dima", 8, 800);

        vacancy.save(misha);
        dima.setId(misha.getId());

        boolean result = vacancy.update(dima);
        Candidate candidateResult = vacancy.getById(dima.getId());

        assertTrue(result);
        assertThat(candidateResult.getName(), is("Dima"));
    }
}