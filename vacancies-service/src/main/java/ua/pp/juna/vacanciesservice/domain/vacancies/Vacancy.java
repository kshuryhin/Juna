package ua.pp.juna.vacanciesservice.domain.vacancies;

import jakarta.persistence.*;
import lombok.*;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer salaryFrom;

    @Column(nullable = false)
    private Integer salaryTo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(nullable = false)
    private EnglishLevel englishLevel;

    @Column(nullable = false, length = 10_000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime datePosted;

    @ManyToMany
    private List<Skill> skills;

    @ManyToOne
    private Employer employer;

    @ManyToMany
    private List<Candidate> candidates;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public Map<Parameter, String> getParams() {
        return Map.of(
                Parameter.CATEGORY, this.category.name(),
                Parameter.COUNTRY, this.country,
                Parameter.SALARY_FROM, String.valueOf(this.salaryFrom),
                Parameter.GRADE, this.grade.name(),
                Parameter.EMPLOYMENT, this.employmentType.name(),
                Parameter.ENGLISH_LEVEL, this.englishLevel.name()
        );
    }
}
