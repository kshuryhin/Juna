package ua.pp.juna.vacanciesservice.service;

import ua.pp.juna.vacanciesservice.domain.Vacancy;

import java.util.Collection;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);

    Vacancy getVacancyById(Long id);

    Collection<Vacancy> getAll();

    Vacancy updateVacancy(Vacancy vacancy, Long id);

    void deleteVacancy(Long id);
}
