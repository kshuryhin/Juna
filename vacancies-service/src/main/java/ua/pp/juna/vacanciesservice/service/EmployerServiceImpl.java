package ua.pp.juna.vacanciesservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.repo.EmployerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService{
    private final EmployerRepository employerRepository;
    @Override
    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer updateEmployer(Employer employer, Long id) {
        final Employer updateEmployer = getEmployerById(id)
                .withCompanyName(employer.getCompanyName())
                .withVacancies(employer.getVacancies());

        return saveEmployer(updateEmployer);
    }

    @Override
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}
