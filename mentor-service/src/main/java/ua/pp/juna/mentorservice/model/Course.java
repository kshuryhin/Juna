package ua.pp.juna.mentorservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Builder
@With
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToOne
    private Mentor mentor;

    @ManyToMany
    private List<Student> students;
}
