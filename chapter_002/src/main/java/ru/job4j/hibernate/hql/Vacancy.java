package ru.job4j.hibernate.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_database_vacancy")
    private DatabaseVacancy databaseVacancy;

    public Vacancy() {
    }

    public Vacancy(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DatabaseVacancy getDatabaseVacancy() {
        return databaseVacancy;
    }

    public void setDatabaseVacancy(DatabaseVacancy databaseVacancy) {
        this.databaseVacancy = databaseVacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id && Objects.equals(description, vacancy.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Vacancy { "
                + "id = " + id
                + ", description = '" + description + '\''
                + '}';
    }
}
