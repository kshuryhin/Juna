package ua.pp.juna.vacanciesservice.service.employers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.repo.employers.EmployerRepository;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployerServiceImpl implements EmployerService{
    private final EmployerRepository employerRepository;

    private final UserDetailsRepository userDetailsRepository;
    @Override
    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id).orElse(null);
    }

    @Override
    public Employer getEmployerByEmail(String email) {
        log.info("Fetching employer by email {}", email);
        return employerRepository.findByUserDetailsEmail(email);
    }

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer updateEmployer(Employer employer, Long id) {
        final Employer updateEmployer = employerRepository.findById(id).get()
                .withCompanyName(employer.getCompanyName())
                .withCompanyDescription(employer.getCompanyDescription())
                .withCompanyWebSite(employer.getCompanyWebSite())
                .withCompanyLogoUrl(employer.getCompanyLogoUrl())
                .withCountry(employer.getCountry())
                .withVacancies(employer.getVacancies())
                .withSavedCandidates(employer.getSavedCandidates());

        final var updatedUserDetails = userDetailsRepository.findById(updateEmployer.getUserDetails().getId()).get()
                .withEmail(employer.getUserDetails().getEmail())
                .withFirstName(employer.getUserDetails().getFirstName())
                .withLastName(employer.getUserDetails().getLastName());

        return saveEmployer(updateEmployer.withUserDetails(updatedUserDetails));
    }

    @Override
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}
