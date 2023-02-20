package ua.pp.juna.vacanciesservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.Vacancy;
import ua.pp.juna.vacanciesservice.repo.VacancyRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    @Override
    public Vacancy createVacancy(final Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy getVacancyById(final Long id) {
        return vacancyRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public Vacancy updateVacancy(final Vacancy vacancy, final Long id) {
        final Vacancy updatedVacancy = getVacancyById(id)
                .withName(vacancy.getName())
                .withActive(vacancy.isActive())
                .withDescription(vacancy.getDescription())
                .withDatePosted(vacancy.getDatePosted());

        return createVacancy(updatedVacancy);
    }

    @Override
    public void deleteVacancy(final Long id) {
        vacancyRepository.deleteById(id);
    }
}
