package ua.pp.juna.vacanciesservice.service.employers;

import ua.pp.juna.vacanciesservice.domain.employers.Employer;

import java.util.List;

public interface EmployerService {
    Employer saveEmployer(Employer employer);

    Employer getEmployerById(Long id);

    Employer getEmployerByEmail(String email);

    List<Employer> getAllEmployers();

    Employer updateEmployer(Employer employer, Long id);

    void deleteEmployer(Long id);
}
