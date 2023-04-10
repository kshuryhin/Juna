package ua.pp.juna.vacanciesservice.domain.employers;


import jakarta.persistence.*;
import lombok.*;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;

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

    private String country;

    @Column(length = 10_000)
    private String companyDescription;

    private String companyWebSite;

    private String companyLogoUrl;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;
    @ManyToMany
    private List<Candidate> savedCandidates;
}
