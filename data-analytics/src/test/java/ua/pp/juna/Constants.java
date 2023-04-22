package ua.pp.juna;

import lombok.experimental.UtilityClass;
import ua.pp.juna.controllers.request.CandidateRequest;
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.domain.VacancyAnalytics;

import java.util.Arrays;
import java.util.UUID;

@UtilityClass
public class Constants {
    public static final CandidateAnalytics CANDIDATE_ANALYTICS = CandidateAnalytics.builder()
            .id(UUID.randomUUID().toString())
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

    public static final VacancyAnalytics VACANCY_ANALYTICS = VacancyAnalytics.builder()
            .id(UUID.randomUUID().toString())
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

    public static final CandidateRequest CANDIDATE_REQUEST = new CandidateRequest(
    "Software Engineer",
            true,
            80000,
            "Senior",
            "USA",
            "New York",
            Arrays.asList("Java", "Spring", "Docker"),
    "John",
            "Doe",
            "john.doe@email.com",
            "IT",
            "Advanced",
            "Full-time",
            "5 years",
            "+1-123-456-7890",
            "Developed an e-commerce application and a personal blog.",
            "https://example.com/photo.jpg",
            "https://linkedin.com/in/johndoe",
            "https://t.me/johndoe",
            "https://github.com/johndoe"
            );

}
