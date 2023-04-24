package ua.pp.juna.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.domain.VacancyAnalytics;
import ua.pp.juna.repositories.VacancyAnalyticsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyAnalyticsService {
    private final VacancyAnalyticsRepository vacancyStatsRepository;

    public List<VacancyAnalytics> getAllVacancyStats() {
        return vacancyStatsRepository.findAll();
    }

    public VacancyAnalytics getVacancyStatsById(String id) {
        return vacancyStatsRepository.findById(id).orElseThrow();
    }

    public VacancyAnalytics getVacancyAnalyticsByOriginId(final Long id){
        return vacancyStatsRepository.findByOriginVacancyId(id);
    }

    public VacancyAnalytics createVacancyStats(VacancyAnalytics vacancyStats) {
        return vacancyStatsRepository.save(vacancyStats.withDatePosted(LocalDateTime.now()));
    }

    public VacancyAnalytics updateVacancyStats(String id, VacancyAnalytics vacancyStatsDetails) {
         VacancyAnalytics vacancyStats = getVacancyStatsById(id);

        vacancyStats.setName(vacancyStatsDetails.getName());
        vacancyStats.setCountry(vacancyStatsDetails.getCountry());
        vacancyStats.setSalaryFrom(vacancyStatsDetails.getSalaryFrom());
        vacancyStats.setSalaryTo(vacancyStatsDetails.getSalaryTo());
        vacancyStats.setGrade(vacancyStatsDetails.getGrade());
        vacancyStats.setEmploymentType(vacancyStatsDetails.getEmploymentType());
        vacancyStats.setEnglishLevel(vacancyStatsDetails.getEnglishLevel());
        vacancyStats.setCategory(vacancyStatsDetails.getCategory());
        vacancyStats.setViews(vacancyStatsDetails.getViews());
        vacancyStats.setApplications(vacancyStatsDetails.getApplications());
        vacancyStats.setSavings(vacancyStatsDetails.getSavings());

        return vacancyStatsRepository.save(vacancyStats);
    }

    public void deleteVacancyStats(String id) {
        VacancyAnalytics vacancyStats = getVacancyStatsById(id);
        vacancyStatsRepository.delete(vacancyStats);
    }
}
