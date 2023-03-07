package ua.pp.juna.vacanciesservice.repo.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;


public interface VacancyRepository extends JpaRepository<Vacancy, Long>{
}
