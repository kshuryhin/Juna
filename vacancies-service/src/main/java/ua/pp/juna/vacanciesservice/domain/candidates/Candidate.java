package ua.pp.juna.vacanciesservice.domain.candidates;

import jakarta.persistence.*;
import lombok.*;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.vacancies.*;

import java.util.List;

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

}
