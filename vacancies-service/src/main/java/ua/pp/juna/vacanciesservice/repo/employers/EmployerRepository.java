package ua.pp.juna.vacanciesservice.repo.employers;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findByUserDetailsEmail(String email);
}
