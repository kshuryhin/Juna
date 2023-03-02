package ua.pp.juna.vacanciesservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @OneToMany
    private List<Vacancy> vacancies;
}
