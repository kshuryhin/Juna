package ua.pp.juna.vacanciesservice.domain;

import jakarta.persistence.*;
import lombok.*;

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

    private String summary;

    private Integer yearsOfExperience;

    private String position;

    private Integer salaryExpectations;

    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @ManyToMany
    private List<Category> categories;

    private String englishLevel;

}
