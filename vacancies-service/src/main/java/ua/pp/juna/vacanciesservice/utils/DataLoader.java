package ua.pp.juna.vacanciesservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.domain.Role;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.vacancies.*;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.service.employers.EmployerService;
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

        final var skillIDs = initSkills();

        final var employerDetails = UserDetails.builder()
                .email("anbro95@gmail.com")
                .firstName("Andrew")
                .lastName("Brovko")
                .isLoggedIn(true)
                .role(Role.EMPLOYERS)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var employer = Employer.builder()
                        .userDetails(employerDetails)
                        .companyName("Google")
                        .companyWebSite("https://www.capgemini.com")
                        .country("France")
                        .companyDescription("TechWave Outsourcing Solutions is a premier IT outsourcing company that specializes in providing cost-effective, high-quality, and scalable technology solutions to businesses worldwide. With a diverse team of skilled professionals and a focus on customer satisfaction, our mission is to empower organizations to maximize their potential through innovative and customized IT services.\n" +
                        "\n" +
                        "We offer a comprehensive range of services, including software development, mobile app development, cloud computing, data analytics, cybersecurity, and IT consulting. By partnering with TechWave, clients gain access to our extensive industry expertise, state-of-the-art infrastructure, and cutting-edge technology that enables them to stay ahead of the competition.\n" +
                        "\n" +
                        "Key Features:\n" +
                        "\n" +
                        "Customized Solutions: Our team collaborates with clients to understand their unique business requirements and develop tailored IT solutions that align with their strategic objectives.\n" +
                        "Quality Assurance: We adhere to strict quality standards and employ rigorous testing methodologies to ensure the delivery of high-performing, reliable, and secure solutions.\n" +
                        "Scalability: TechWave's flexible engagement models enable businesses to scale their IT infrastructure seamlessly as they grow, without compromising on quality or performance.\n" +
                        "Global Presence: With offices in multiple locations, our clients can leverage our global talent pool and industry insights to stay ahead of the curve.\n" +
                        "Cost-Effective: Our competitive pricing models and innovative solutions help businesses reduce their IT expenses while maintaining the highest quality standards.\n" +
                        "Dedicated Support: Our 24/7 customer support team ensures that clients receive prompt assistance and guidance throughout the project lifecycle, fostering long-lasting partnerships.\n" +
                        "By choosing TechWave Outsourcing Solutions, businesses can focus on their core competencies while we take care of their IT needs, enabling them to achieve their goals and drive success in today's fast-paced digital landscape.")
                        .build();

        final var skill1 = skillsService.getSkillById(1L);
        final var skill2 = skillsService.getSkillById(2L);
        final var skill3 = skillsService.getSkillById(3L);

        initCandidates(skill1, skill2, skill3);
        employerService.saveEmployer(employer);

        initVacancies(employer, List.of(skill1, skill2, skill3));
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

    private void initCandidates(final Skill...skills){
        final var skill1 = skills[0];
        final var skill2 = skills[1];
        final var skill3 = skills[2];
        final var candidateDetails = UserDetails.builder()
                .email("ksurygin5@gmail.com")
                .firstName("Kostiantyn")
                .lastName("Shuryhin")
                .isLoggedIn(true)
                .role(Role.CANDIDATES)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var candidateDetails2 = UserDetails.builder()
                .email("biba0202@gmail.com")
                .firstName("Pavel")
                .lastName("Till")
                .isLoggedIn(true)
                .role(Role.CANDIDATES)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var candidateDetails3 = UserDetails.builder()
                .email("samwell@gmail.com")
                .firstName("Sam")
                .lastName("Harris")
                .isLoggedIn(true)
                .role(Role.CANDIDATES)
                .password(passwordEncoder.encode("1234"))
                .build();
        final var candidate = Candidate.builder()
                .userDetails(candidateDetails)
                .englishLevel(EnglishLevel.ADVANCED)
                .city("Odesa")
                .position("Java Developer")
                .grade(Grade.JUNIOR)
                .country("Ukraine")
                .phoneNumber("+380951427261")
                .category(Category.JAVA)
                .employmentType(EmploymentType.ALL)
                .photoLink("kostya.jpg")
                .isActive(true)
                .telegramLink("http://t.me/kostya2234")
                .linkedinLink("https://www.linkedin.com/in/kostyan21/")
                .githubLink("https://github.com/Shkaff02")
                .salaryExpectations(2000)
                .petProjectsDescription("As a Junior Java Developer, one potential pet project you could consider is a simple web-based application that helps users track their daily water intake. The application would allow users to input the amount of water they drink throughout the day and keep a running total of their intake.\n" +
                        "\n" +
                        "Here are some features you could include in your water tracking application:\n" +
                        "\n" +
                        "User authentication: Allow users to create accounts and log in to their personal water tracking dashboard.\n" +
                        "Water intake tracker: Provide a simple form where users can enter the amount of water they have consumed at different times of the day.\n" +
                        "Daily summary: Show users a summary of their water intake for the day, including a percentage of their daily recommended intake.\n" +
                        "Weekly and monthly reports: Allow users to view their water intake over longer periods of time to help them identify trends and set goals.\n" +
                        "Reminders: Allow users to set reminders to drink water throughout the day, and send push notifications to their devices to remind them to stay hydrated.\n" +
                        "Gamification: Consider adding some fun and engaging elements to your application to help motivate users to drink more water, such as rewards or challenges.\n" +
                        "By building a simple but useful application like this, you can gain valuable experience working with Java and web development technologies, and create something that could potentially benefit others.")
                .workExperience("A Junior Java developer typically has limited professional experience but possesses the fundamental skills necessary to write Java code and work within a development team. Here is an example work experience description for a Junior Java developer:\n" +
                        "\n" +
                        "Junior Java Developer\n" +
                        "ABC Company\n" +
                        "June 2021 - Present\n" +
                        "\n" +
                        "Work collaboratively with a team of developers to design and develop software solutions using Java technologies.\n" +
                        "Participate in code reviews and debugging sessions to ensure code quality and optimize system performance.\n" +
                        "Develop and maintain database schemas, and work with SQL databases and data modeling tools.\n" +
                        "Develop and maintain RESTful web services and APIs using Spring Framework.\n" +
                        "Write and maintain unit tests using JUnit and Mockito frameworks.\n" +
                        "Contribute to continuous integration and delivery pipelines using GitLab and Jenkins.\n" +
                        "Collaborate with other teams, including QA and project management, to ensure timely and effective delivery of software solutions.\n" +
                        "Stay up-to-date with emerging trends and technologies in software development and contribute to knowledge-sharing sessions within the team.\n" +
                        "Note that this is just an example and actual job descriptions may vary depending on the company and specific job requirements.")
                .build();
        final var candidate2 = Candidate.builder()
                .userDetails(candidateDetails2)
                .englishLevel(EnglishLevel.UPPER_INTERMEDIATE)
                .city("Berlin")
                .position("Python Developer")
                .grade(Grade.MIDDLE)
                .country("Germany")
                .isActive(true)
                .phoneNumber("+380951427261")
                .category(Category.PYTHON)
                .employmentType(EmploymentType.REMOTE)
                .photoLink("kostya.jpg")
                .salaryExpectations(1500)
                .telegramLink("http://t.me/kostya2234")
                .linkedinLink("https://www.linkedin.com/in/kostyan21/")
                .githubLink("https://github.com/Shkaff02")
                .petProjectsDescription("As a Junior Java Developer, one potential pet project you could consider is a simple web-based application that helps users track their daily water intake. The application would allow users to input the amount of water they drink throughout the day and keep a running total of their intake.\n" +
                        "\n" +
                        "Here are some features you could include in your water tracking application:\n" +
                        "\n" +
                        "User authentication: Allow users to create accounts and log in to their personal water tracking dashboard.\n" +
                        "Water intake tracker: Provide a simple form where users can enter the amount of water they have consumed at different times of the day.\n" +
                        "Daily summary: Show users a summary of their water intake for the day, including a percentage of their daily recommended intake.\n" +
                        "Weekly and monthly reports: Allow users to view their water intake over longer periods of time to help them identify trends and set goals.\n" +
                        "Reminders: Allow users to set reminders to drink water throughout the day, and send push notifications to their devices to remind them to stay hydrated.\n" +
                        "Gamification: Consider adding some fun and engaging elements to your application to help motivate users to drink more water, such as rewards or challenges.\n" +
                        "By building a simple but useful application like this, you can gain valuable experience working with Java and web development technologies, and create something that could potentially benefit others.")
                .workExperience("A Junior Java developer typically has limited professional experience but possesses the fundamental skills necessary to write Java code and work within a development team. Here is an example work experience description for a Junior Java developer:\n" +
                        "\n" +
                        "Junior Java Developer\n" +
                        "ABC Company\n" +
                        "June 2021 - Present\n" +
                        "\n" +
                        "Work collaboratively with a team of developers to design and develop software solutions using Java technologies.\n" +
                        "Participate in code reviews and debugging sessions to ensure code quality and optimize system performance.\n" +
                        "Develop and maintain database schemas, and work with SQL databases and data modeling tools.\n" +
                        "Develop and maintain RESTful web services and APIs using Spring Framework.\n" +
                        "Write and maintain unit tests using JUnit and Mockito frameworks.\n" +
                        "Contribute to continuous integration and delivery pipelines using GitLab and Jenkins.\n" +
                        "Collaborate with other teams, including QA and project management, to ensure timely and effective delivery of software solutions.\n" +
                        "Stay up-to-date with emerging trends and technologies in software development and contribute to knowledge-sharing sessions within the team.\n" +
                        "Note that this is just an example and actual job descriptions may vary depending on the company and specific job requirements.")
                .build();
        final var candidate3 = Candidate.builder()
                .userDetails(candidateDetails3)
                .englishLevel(EnglishLevel.BEGINNER)
                .city("Lviv")
                .position("PHP Developer")
                .grade(Grade.TRAINEE)
                .country("Ukraine")
                .phoneNumber("+380951427261")
                .category(Category.PHP)
                .isActive(true)
                .employmentType(EmploymentType.OFFICE)
                .salaryExpectations(900)
                .telegramLink("http://t.me/kostya2234")
                .linkedinLink("https://www.linkedin.com/in/kostyan21/")
                .githubLink("https://github.com/Shkaff02")
                .petProjectsDescription("As a Junior Java Developer, one potential pet project you could consider is a simple web-based application that helps users track their daily water intake. The application would allow users to input the amount of water they drink throughout the day and keep a running total of their intake.\n" +
                        "\n" +
                        "Here are some features you could include in your water tracking application:\n" +
                        "\n" +
                        "User authentication: Allow users to create accounts and log in to their personal water tracking dashboard.\n" +
                        "Water intake tracker: Provide a simple form where users can enter the amount of water they have consumed at different times of the day.\n" +
                        "Daily summary: Show users a summary of their water intake for the day, including a percentage of their daily recommended intake.\n" +
                        "Weekly and monthly reports: Allow users to view their water intake over longer periods of time to help them identify trends and set goals.\n" +
                        "Reminders: Allow users to set reminders to drink water throughout the day, and send push notifications to their devices to remind them to stay hydrated.\n" +
                        "Gamification: Consider adding some fun and engaging elements to your application to help motivate users to drink more water, such as rewards or challenges.\n" +
                        "By building a simple but useful application like this, you can gain valuable experience working with Java and web development technologies, and create something that could potentially benefit others.")
                .workExperience("A Junior Java developer typically has limited professional experience but possesses the fundamental skills necessary to write Java code and work within a development team. Here is an example work experience description for a Junior Java developer:\n" +
                        "\n" +
                        "Junior Java Developer\n" +
                        "ABC Company\n" +
                        "June 2021 - Present\n" +
                        "\n" +
                        "Work collaboratively with a team of developers to design and develop software solutions using Java technologies.\n" +
                        "Participate in code reviews and debugging sessions to ensure code quality and optimize system performance.\n" +
                        "Develop and maintain database schemas, and work with SQL databases and data modeling tools.\n" +
                        "Develop and maintain RESTful web services and APIs using Spring Framework.\n" +
                        "Write and maintain unit tests using JUnit and Mockito frameworks.\n" +
                        "Contribute to continuous integration and delivery pipelines using GitLab and Jenkins.\n" +
                        "Collaborate with other teams, including QA and project management, to ensure timely and effective delivery of software solutions.\n" +
                        "Stay up-to-date with emerging trends and technologies in software development and contribute to knowledge-sharing sessions within the team.\n" +
                        "Note that this is just an example and actual job descriptions may vary depending on the company and specific job requirements.")
                .build();
        candidateService.saveCandidate(candidate2);
        candidateService.saveCandidate(candidate3);
        candidateService.saveCandidate(candidate);
        candidateService.updateCandidate(candidate.withSkills(List.of(skill1, skill2, skill3)), candidate.getId());
        candidateService.updateCandidate(candidate2.withSkills(List.of(skill1)), candidate2.getId());
        candidateService.updateCandidate(candidate3.withSkills(List.of(skill1, skill3)), candidate3.getId());
    }
    private void initVacancies(final Employer employer, final List<Skill> skills) {
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
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
                .employer(employer)
                .skills(skills)
                .grade(Grade.SENIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.UPPER_INTERMEDIATE)
                .category(Category.DEVOPS)
                .description(description)
                .datePosted(LocalDateTime.now())
                .build();


        vacancyService.createVacancy(vacancy1);
        vacancyService.createVacancy(vacancy2);
        vacancyService.createVacancy(vacancy3);
        vacancyService.createVacancy(vacancy4);
        vacancyService.createVacancy(vacancy5);
        vacancyService.createVacancy(vacancy6);
        vacancyService.createVacancy(vacancy7);
    }
}
