package ua.pp.juna.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.pp.juna.domain.VacancyAnalytics;

@Repository
public interface VacancyAnalyticsRepository extends MongoRepository<VacancyAnalytics, String> {
    VacancyAnalytics findByName(String name);

    VacancyAnalytics findByOriginVacancyId(Long id);
}
