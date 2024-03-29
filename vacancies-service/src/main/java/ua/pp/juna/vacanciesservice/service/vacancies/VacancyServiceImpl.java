package ua.pp.juna.vacanciesservice.service.vacancies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;
import ua.pp.juna.vacanciesservice.repo.candidates.CandidateRepository;
import ua.pp.juna.vacanciesservice.repo.employers.EmployerRepository;
import ua.pp.juna.vacanciesservice.repo.vacancies.VacancyRepository;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;

    private final CandidateRepository candidateRepository;

    private final EmployerRepository employerRepository;
    @Override
    public Vacancy createVacancy(final Vacancy vacancy) {
        log.info("Persisting vacancy {}", vacancy);
        return vacancyRepository.save(
                vacancy
                .withDatePosted(LocalDateTime.now()));
    }

    @Override
    public Vacancy getVacancyById(final Long id) {
        log.info("Fetching vacancy with id {}", id);
        return vacancyRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Vacancy> getAll(final Map<Parameter, String> params) {
        final var filteredParams =  params.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (filteredParams.isEmpty()) {
            log.info("Fetching all vacancies");
            return vacancyRepository.findAll();
        }

        log.info("Fetching vacancies by parameters {}", params.keySet());
        return vacancyRepository.findAll().stream()
                .filter(vacancy -> compareParams(vacancy, filteredParams))
                .toList();
    }

    @Override
    public Collection<Vacancy> getAllByCandidate(Long candidateId) {
        log.info("Fetching candidate with id {}", candidateId);
        final var candidate = candidateRepository.findById(candidateId).orElse(null);
        log.info("Fetching all vacancies by candidate {}", candidate);
        return vacancyRepository.findAllByCandidates(candidate);
    }

    @Override
    public Collection<Vacancy> getAllBySaver(Long saverId) {
        log.info("Fetching candidate with id {}", saverId);
        final var saver = candidateRepository.findById(saverId).orElse(null);
        log.info("Fetching all vacancies by saver {}", saver);
        return vacancyRepository.findAllBySavers(saver);
    }

    @Override
    public Collection<Vacancy> getAllByEmployer(final Long id) {
        final var employer = employerRepository.findById(id).orElse(null);
        log.info("Fetching all vacancies by employer {}", employer);
        return vacancyRepository.findAllByEmployer(employer);
    }

    @Override
    public Vacancy updateVacancy(final Vacancy vacancy, final Long id) {
        log.info("Updating vacancy with id {} on {}", id, vacancy);

        final var updatedVacancy = getVacancyById(id)
                .withName(vacancy.getName())
                .withCountry(vacancy.getCountry())
                .withSalaryFrom(vacancy.getSalaryFrom())
                .withSalaryTo(vacancy.getSalaryTo())
                .withGrade(vacancy.getGrade())
                .withEmploymentType(vacancy.getEmploymentType())
                .withEnglishLevel(vacancy.getEnglishLevel())
                .withDescription(vacancy.getDescription())
                .withSkills(vacancy.getSkills())
                .withCandidates(vacancy.getCandidates())
                .withSavers(vacancy.getSavers());

        return vacancyRepository.save(updatedVacancy);
    }

    @Override
    public void deleteVacancy(final Long id) {
        log.info("Deleting vacancy with id {}", id);
        vacancyRepository.deleteById(id);
    }

    private boolean compareParams(final Vacancy vacancy, final Map<Parameter, String> params) {
        if (!compareSalaryRange(vacancy, params))
            return false;

        for (Map.Entry<Parameter, String> entry:params.entrySet()){
            final var parameterName = entry.getKey();
            final var parameterValue = entry.getValue();
            if (parameterName == Parameter.SALARY_FROM)
                continue;

            if (!vacancy.getParams().get(parameterName).equals(parameterValue))
                return false;
        }
        return true;
    }

    private boolean compareSalaryRange(final Vacancy vacancy, final Map<Parameter, String> params) {
        if (!params.containsKey(Parameter.SALARY_FROM))
            return true;
        final var salaryFrom = Integer.valueOf(params.get(Parameter.SALARY_FROM));

        return salaryFrom >= vacancy.getSalaryFrom() && salaryFrom <= vacancy.getSalaryTo();
    }
}
