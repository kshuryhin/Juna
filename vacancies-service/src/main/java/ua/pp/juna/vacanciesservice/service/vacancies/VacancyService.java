package ua.pp.juna.vacanciesservice.service.vacancies;

import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);

    Vacancy getVacancyById(Long id);

    Collection<Vacancy> getAll(Map<Parameter, String> params);

    Collection<Vacancy> getAllByCandidate(Long candidateId);

    Collection<Vacancy> getAllBySaver(Long saverId);

    Collection<Vacancy> getAllByEmployer(Long id);

    Vacancy updateVacancy(Vacancy vacancy, Long id);

    void deleteVacancy(Long id);
}
