package ua.pp.juna.vacanciesservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>{
}
