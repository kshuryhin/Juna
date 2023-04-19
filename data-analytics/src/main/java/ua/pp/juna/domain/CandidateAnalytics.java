package ua.pp.juna.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidate_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class CandidateAnalytics {
    @Id
    private String id;

    @Indexed
    private String email;

    private String position;

    private Boolean isActive;

    private Integer salaryExpectations;

    private String grade;

    private String country;

    private String category;

    private String englishLevel;

    private String employmentType;
}
