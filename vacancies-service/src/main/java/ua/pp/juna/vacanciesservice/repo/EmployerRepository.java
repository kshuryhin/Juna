package ua.pp.juna.vacanciesservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
