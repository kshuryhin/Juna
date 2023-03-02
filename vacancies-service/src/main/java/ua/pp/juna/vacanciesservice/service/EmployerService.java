package ua.pp.juna.vacanciesservice.service;

import ua.pp.juna.vacanciesservice.domain.Employer;

import java.util.List;

public interface EmployerService {
    Employer saveEmployer(Employer employer);

    Employer getEmployerById(Long id);

    List<Employer> getAllEmployers();

    Employer updateEmployer(Employer employer, Long id);

    void deleteEmployer(Long id);
}
