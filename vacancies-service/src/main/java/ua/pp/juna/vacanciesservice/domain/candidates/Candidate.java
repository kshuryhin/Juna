package ua.pp.juna.vacanciesservice.domain.candidates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.vacancies.*;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    private Integer salaryExpectations;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private String country;

    private String city;

    @ManyToMany
    private List<Skill> skills;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private EnglishLevel englishLevel;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(length = 10_000)
    private String workExperience;

    private String phoneNumber;

    @Column(length = 10_000)
    private String petProjectsDescription;

    private String photoLink;

    @JsonIgnore
    public Map<Parameter, String> getParams() {
        return Map.of(
                Parameter.CATEGORY, this.category.name(),
                Parameter.COUNTRY, this.country,
                Parameter.SALARY_EXPECTATIONS, String.valueOf(this.salaryExpectations),
                Parameter.GRADE, this.grade.name(),
                Parameter.EMPLOYMENT, this.employmentType.name(),
                Parameter.ENGLISH_LEVEL, this.englishLevel.name()
        );
    }

}
