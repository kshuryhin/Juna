package models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidates")
@Data
@RequiredArgsConstructor
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

}
