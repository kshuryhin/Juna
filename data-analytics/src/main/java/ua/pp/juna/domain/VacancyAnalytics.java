package ua.pp.juna.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "vacancy_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class VacancyAnalytics {
    @Id
    private String id;
    @Indexed
    private Long originVacancyId;
    private String name;
    private String country;
    private Integer salaryFrom;
    private Integer salaryTo;
    private String grade;
    private String employmentType;
    private String englishLevel;
    private String category;
    private LocalDateTime datePosted;
    private Integer views;
    private Integer applications;
    private Integer savings;
}
