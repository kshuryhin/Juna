package ua.pp.juna.vacanciesservice.service.vacancies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;
import ua.pp.juna.vacanciesservice.repo.EmployerRepository;
import ua.pp.juna.vacanciesservice.repo.vacancies.SkillsRepository;
import ua.pp.juna.vacanciesservice.repo.vacancies.VacancyRepository;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final EmployerRepository employerRepository;

    private final SkillsRepository skillsRepository;
    @Override
    public Vacancy createVacancy(final Vacancy vacancy, final Long employerId, final List<Long> skillId) {
        log.info("Persisting vacancy {}", vacancy);
        final var employer = employerRepository.findById(employerId).get();
        final var skills = skillsRepository.findAllById(skillId);
        return vacancyRepository.save(
                vacancy
                    .withSkills(skills)
                .withEmployer(employer)
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
                .withCandidates(vacancy.getCandidates());

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
