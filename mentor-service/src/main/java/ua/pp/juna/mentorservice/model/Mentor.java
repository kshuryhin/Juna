package ua.pp.juna.mentorservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentors")
@Builder
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(length = 10_000)
    private String description;

    private String password;

    private boolean isLoggedIn;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String email;
    private String linkedin;
    private String github;
    private String imageLink;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "mentorId")
    private List<Course> courses;

}
