package ua.pp.juna.controllers.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@With
public class CandidateRequest {
    private String position;

    private Boolean isActive;

    private Integer salaryExpectations;

    private String grade;

    private String country;

    private String city;

    private List<String> skills;

    private String firstName;

    private String lastName;

    private String email;

    private String category;

    private String englishLevel;

    private String employmentType;

    private String workExperience;

    private String phoneNumber;

    private String petProjectsDescription;

    private String photoLink;

    private String linkedinLink;

    private String telegramLink;

    private String githubLink;
}
