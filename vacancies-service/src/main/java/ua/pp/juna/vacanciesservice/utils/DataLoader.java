package ua.pp.juna.vacanciesservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.pp.juna.vacanciesservice.domain.Candidate;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.domain.Role;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.vacancies.*;
import ua.pp.juna.vacanciesservice.service.CandidateService;
import ua.pp.juna.vacanciesservice.service.EmployerService;
import ua.pp.juna.vacanciesservice.service.vacancies.SkillsService;
import ua.pp.juna.vacanciesservice.service.vacancies.VacancyService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final CandidateService candidateService;
    private final EmployerService employerService;
    private final VacancyService vacancyService;
    private final SkillsService skillsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        final var candidateDetails = UserDetails.builder()
                .email("ksurygin5@gmail.com")
                .firstName("Kostiantyn")
                .lastName("Shuryhin")
                .isLoggedIn(true)
                .role(Role.CANDIDATES)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var employerDetails = UserDetails.builder()
                .email("anbro95@gmail.com")
                .firstName("Andrew")
                .lastName("Brovko")
                .isLoggedIn(true)
                .role(Role.EMPLOYERS)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var candidate = Candidate.builder()
                .userDetails(candidateDetails)
                .englishLevel(EnglishLevel.ADVANCED)
                .city("Odesa")
                .yearsOfExperience(3).build();
        final var employer = Employer.builder()
                        .userDetails(employerDetails)
                        .companyName("Google")
                        .build();


        candidateService.saveCandidate(candidate);
        final var employerId = employerService.saveEmployer(employer).getId();

        final var skillIDs = initSkills();
        initVacancies(employerId, skillIDs);
    }

    private List<Long> initSkills(){
        final var skill1 = Skill.builder()
                .name("qa")
                .build();
        final var skill2 = Skill.builder()
                .name("docker")
                .build();
        final var skill3 = Skill.builder()
                .name("kubernetes")
                .build();

        final var skill1Id = skillsService.saveSkill(skill1).getId();
        final var skill2Id = skillsService.saveSkill(skill2).getId();
        final var skill3Id = skillsService.saveSkill(skill3).getId();

        return List.of(skill1Id, skill2Id, skill3Id);
    }
    private void initVacancies(final Long employer, final List<Long> skillList) {
        final var description = "We are seeking a highly skilled software developer to join our team. The ideal candidate will have experience in developing software solutions using a variety of programming languages and platforms. The candidate will be responsible for designing, developing, and implementing software solutions that meet the needs of our clients.\n" +
                "\n" +
                "Responsibilities:\n" +
                "\n" +
                "Develop and maintain software applications using a variety of programming languages and platforms.\n" +
                "Participate in the design and architecture of software solutions.\n" +
                "Collaborate with other developers to ensure that software solutions are integrated effectively.\n" +
                "Troubleshoot and resolve software defects and issues.\n" +
                "Conduct code reviews to ensure that software solutions are of high quality and maintainable.\n" +
                "Keep up-to-date with emerging trends and technologies in software development." +
                "We offer competitive salary packages, flexible working hours, and opportunities for growth and development within the company. If you are passionate about software development and want to work in a dynamic and challenging environment, we encourage you to apply.";
        final var vacancy1 = Vacancy.builder()
                .name("Java Developer")
                .country("Ukraine")
                .salaryFrom(1000)
                .salaryTo(3000)
                .grade(Grade.JUNIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.JAVA)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy2 = Vacancy.builder()
                .name("Python Developer")
                .country("Ukraine")
                .salaryFrom(1300)
                .salaryTo(2900)
                .grade(Grade.MIDDLE)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.UPPER_INTERMEDIATE)
                .category(Category.PYTHON)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy3 = Vacancy.builder()
                .name("Java Developer")
                .country("Germany")
                .salaryFrom(2400)
                .salaryTo(3600)
                .grade(Grade.SENIOR)
                .employmentType(EmploymentType.OFFICE)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.JAVA)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy4 = Vacancy.builder()
                .name("PHP Developer")
                .country("USA")
                .salaryFrom(900)
                .salaryTo(1420)
                .grade(Grade.JUNIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.PHP)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy5 = Vacancy.builder()
                .name("PHP Developer")
                .country("Ukraine")
                .salaryFrom(500)
                .salaryTo(900)
                .grade(Grade.TRAINEE)
                .employmentType(EmploymentType.OFFICE)
                .englishLevel(EnglishLevel.BEGINNER)
                .category(Category.PHP)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy6 = Vacancy.builder()
                .name("JS Developer")
                .country("Germany")
                .salaryFrom(1320)
                .salaryTo(2930)
                .grade(Grade.MIDDLE)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.PRE_INTERMEDIATE)
                .category(Category.JS)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();
        final var vacancy7 = Vacancy.builder()
                .name("Devops Engineer")
                .country("Germany")
                .salaryFrom(5000)
                .salaryTo(7000)
                .grade(Grade.SENIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.UPPER_INTERMEDIATE)
                .category(Category.DEVOPS)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();


        vacancyService.createVacancy(vacancy1, employer, skillList);
        vacancyService.createVacancy(vacancy2, employer, skillList);
        vacancyService.createVacancy(vacancy3, employer, skillList);
        vacancyService.createVacancy(vacancy4, employer, skillList);
        vacancyService.createVacancy(vacancy5, employer, skillList);
        vacancyService.createVacancy(vacancy6, employer, skillList);
        vacancyService.createVacancy(vacancy7, employer, skillList);
    }
}
