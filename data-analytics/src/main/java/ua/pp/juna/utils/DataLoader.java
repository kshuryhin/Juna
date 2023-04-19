package ua.pp.juna.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.domain.VacancyAnalytics;
import ua.pp.juna.services.CandidateAnalyticsService;
import ua.pp.juna.services.VacancyAnalyticsService;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final VacancyAnalyticsService vacancyAnalyticsService;

    private final CandidateAnalyticsService candidateAnalyticsService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initVacancyAnalytics();
        initCandidateAnalytics();
    }

    private void initVacancyAnalytics(){
        final var analytics1 = VacancyAnalytics.builder()
                .name("Java Developer")
                .country("Ukraine")
                .salaryFrom(1000)
                .salaryTo(3000)
                .grade("JUNIOR")
                .employmentType("REMOTE")
                .englishLevel("ADVANCED")
                .category("JAVA")
                .views(15)
                .savings(0)
                .applications(35)
                .originVacancyId(1L)
                .build();
        final var analytics2 = VacancyAnalytics.builder()
                .name("Python Developer")
                .country("Ukraine")
                .salaryFrom(1300)
                .salaryTo(2900)
                .grade("MIDDLE")
                .employmentType("REMOTE")
                .englishLevel("UPPER_INTERMEDIATE")
                .category("PYTHON")
                .views(56)
                .savings(0)
                .applications(13)
                .originVacancyId(2L)
                .build();
        final var analytics3 = VacancyAnalytics.builder()
                .name("Java Developer")
                .country("Germany")
                .salaryFrom(2400)
                .salaryTo(3600)
                .grade("SENIOR")
                .employmentType("OFFICE")
                .englishLevel("ADVANCED")
                .category("JAVA")
                .views(17)
                .savings(0)
                .applications(6)
                .originVacancyId(3L)
                .build();
        final var analytics4 = VacancyAnalytics.builder()
                .name("PHP Developer")
                .country("USA")
                .salaryFrom(900)
                .salaryTo(1420)
                .grade("JUNIOR")
                .employmentType("REMOTE")
                .englishLevel("ADVANCED")
                .category("PHP")
                .views(176)
                .savings(0)
                .applications(78)
                .originVacancyId(4L)
                .build();
        final var analytics5 = VacancyAnalytics.builder()
                .name("PHP Developer")
                .country("Ukraine")
                .salaryFrom(500)
                .salaryTo(900)
                .grade("TRAINEE")
                .employmentType("OFFICE")
                .englishLevel("BEGINNER")
                .category("PHP")
                .views(231)
                .savings(0)
                .applications(156)
                .originVacancyId(5L)
                .build();
        final var analytics6 = VacancyAnalytics.builder()
                .name("JS Developer")
                .country("Germany")
                .salaryFrom(1320)
                .salaryTo(2930)
                .grade("MIDDLE")
                .employmentType("REMOTE")
                .englishLevel("PRE_INTERMEDIATE")
                .category("JS")
                .views(69)
                .savings(0)
                .applications(21)
                .originVacancyId(6L)
                .build();
        final var analytics7 = VacancyAnalytics.builder()
                .name("Devops Engineer")
                .country("Germany")
                .salaryFrom(5000)
                .salaryTo(7000)
                .grade("SENIOR")
                .employmentType("REMOTE")
                .englishLevel("UPPER_INTERMEDIATE")
                .category("DEVOPS")
                .views(23)
                .savings(0)
                .applications(8)
                .originVacancyId(7L)
                .build();

        vacancyAnalyticsService.createVacancyStats(analytics1);
        vacancyAnalyticsService.createVacancyStats(analytics2);
        vacancyAnalyticsService.createVacancyStats(analytics3);
        vacancyAnalyticsService.createVacancyStats(analytics4);
        vacancyAnalyticsService.createVacancyStats(analytics5);
        vacancyAnalyticsService.createVacancyStats(analytics6);
        vacancyAnalyticsService.createVacancyStats(analytics7);

    }

    private void initCandidateAnalytics(){
        final var candidate1 = CandidateAnalytics.builder()
                .email("biba0202@gmail.com")
                .position("Python Developer")
                .isActive(true)
                .salaryExpectations(1500)
                .grade("MIDDLE")
                .country("Germany")
                .category("PYTHON")
                .englishLevel("UPPER_INTERMEDIATE")
                .employmentType("REMOTE")
                .build();
        final var candidate2 = CandidateAnalytics.builder()
                .email("samwell@gmail.com")
                .position("PHP Developer")
                .isActive(true)
                .salaryExpectations(900)
                .grade("TRAINEE")
                .country("Ukraine")
                .category("PHP")
                .englishLevel("BEGINNER")
                .employmentType("OFFICE")
                .build();
        final var candidate3 = CandidateAnalytics.builder()
                .email("ksurygin5@gmail.com")
                .position("Java Developer")
                .isActive(true)
                .salaryExpectations(2000)
                .grade("JUNIOR")
                .country("Ukraine")
                .category("JAVA")
                .englishLevel("ADVANCED")
                .employmentType("ALL")
                .build();

        candidateAnalyticsService.createCandidateAnalytics(candidate1);
        candidateAnalyticsService.createCandidateAnalytics(candidate2);
        candidateAnalyticsService.createCandidateAnalytics(candidate3);
    }
}
