package ua.pp.juna.vacanciesservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
}
