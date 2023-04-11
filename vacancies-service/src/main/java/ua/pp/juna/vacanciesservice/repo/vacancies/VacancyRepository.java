package ua.pp.juna.vacanciesservice.repo.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;

import java.util.List;


public interface VacancyRepository extends JpaRepository<Vacancy, Long>{
    List<Vacancy> findAllByCandidates(Candidate candidate);

    List<Vacancy> findAllBySavers(Candidate saver);

    List<Vacancy> findAllByEmployer(Employer employer);
}
