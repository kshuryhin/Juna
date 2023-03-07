package ua.pp.juna.vacanciesservice.services.vacancies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.domain.vacancies.*;
import ua.pp.juna.vacanciesservice.repo.EmployerRepository;
import ua.pp.juna.vacanciesservice.repo.vacancies.SkillsRepository;
import ua.pp.juna.vacanciesservice.repo.vacancies.VacancyRepository;
import ua.pp.juna.vacanciesservice.service.vacancies.VacancyService;
import ua.pp.juna.vacanciesservice.service.vacancies.VacancyServiceImpl;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.pp.juna.vacanciesservice.domain.vacancies.Category.JAVA;
import static ua.pp.juna.vacanciesservice.domain.vacancies.EmploymentType.REMOTE;
import static ua.pp.juna.vacanciesservice.domain.vacancies.EnglishLevel.BEGINNER;
import static ua.pp.juna.vacanciesservice.domain.vacancies.Grade.JUNIOR;

class VacanciesServiceTest {

    private static final String COUNTRY = "Ukraine";

    private static final Category CATEGORY = JAVA;

    private static final Grade GRADE = JUNIOR;

    private static final EnglishLevel ENGLISH_LEVEL = BEGINNER;

    private static final EmploymentType EMPLOYMENT_TYPE = REMOTE;

    private static final Integer SALARY_FROM = 1000;

    private static final Integer SALARY_TO = 2000;

    private VacancyService vacancyService;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private SkillsRepository skillsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.vacancyService = new VacancyServiceImpl(vacancyRepository, employerRepository, skillsRepository);
    }

    @Test
    void createVacancy_happyPath() {
        //arrange
        final var employer = Employer.builder()
                .companyName("test company name")
                .build();
        final var skills = List.of(Skill.builder().name("docker").build());
        final var expected = Vacancy.builder()
                .country(COUNTRY)
                .category(CATEGORY)
                .id(1L)
                .employer(employer)
                .grade(GRADE)
                .englishLevel(ENGLISH_LEVEL)
                .description("Test description")
                .name("Junior Java Developer")
                .datePosted(LocalDateTime.now())
                .candidates(Collections.emptyList())
                .salaryFrom(SALARY_FROM)
                .salaryTo(SALARY_TO)
                .skills(skills)
                .build();
        final var employerId = 1L;
        final var skillsId = List.of(1L,2L,3L);

        when(employerRepository.findById(employerId)).thenReturn(Optional.of(employer));
        when(skillsRepository.findAllById(skillsId)).thenReturn(skills);
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(expected);

        //act
        final var actual = vacancyService.createVacancy(expected, employerId, skillsId);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getVacancyById_happyPath() {
        //arrange
        final var id = 1L;
        final var expected = Vacancy.builder()
                .country(COUNTRY)
                .category(CATEGORY)
                .id(id)
                .employer(Employer.builder().build())
                .grade(GRADE)
                .englishLevel(ENGLISH_LEVEL)
                .description("Test description")
                .name("Junior Java Developer")
                .datePosted(LocalDateTime.now())
                .candidates(Collections.emptyList())
                .salaryFrom(SALARY_FROM)
                .salaryTo(SALARY_TO)
                .skills(List.of(Skill.builder().build()))
                .build();

        when(vacancyRepository.findById(id)).thenReturn(Optional.of(expected));

        //act
        final var actual = vacancyService.getVacancyById(id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAll_withoutParams_happyPath() {
        //arrange
        final var expected = List.of(
                Vacancy.builder().build()
        );
        final var map = new HashMap<Parameter, String>();
        map.put(Parameter.CATEGORY, null);

        when(vacancyRepository.findAll()).thenReturn(expected);

        //act
        final var actual = vacancyService.getAll(map);

        //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getAll_withParams_happyPath() {
        //arrange
        final var vacancy1 = Vacancy.builder()
                .category(CATEGORY)
                .country(COUNTRY)
                .englishLevel(ENGLISH_LEVEL)
                .grade(GRADE)
                .salaryFrom(SALARY_FROM)
                .salaryTo(SALARY_TO)
                .employmentType(EMPLOYMENT_TYPE).build();
        final var vacancy2 = Vacancy.builder()
                .category(Category.GAME)
                .country("Germany")
                .englishLevel(EnglishLevel.ADVANCED)
                .grade(Grade.SENIOR)
                .salaryFrom(3000)
                .salaryTo(5000)
                .employmentType(EMPLOYMENT_TYPE).build();
        final var vacancy3 = Vacancy.builder()
                .category(CATEGORY)
                .country(COUNTRY)
                .englishLevel(ENGLISH_LEVEL)
                .grade(GRADE)
                .salaryFrom(SALARY_FROM)
                .salaryTo(SALARY_TO)
                .employmentType(EMPLOYMENT_TYPE).build();
        final var vacancy4 = Vacancy.builder()
                .category(CATEGORY)
                .country(COUNTRY)
                .englishLevel(ENGLISH_LEVEL)
                .grade(Grade.TRAINEE)
                .salaryFrom(SALARY_FROM)
                .salaryTo(SALARY_TO)
                .employmentType(EMPLOYMENT_TYPE).build();
        final var vacancies = List.of(vacancy1, vacancy2, vacancy3, vacancy4);
        final var expected = List.of(vacancy1, vacancy3);
        final var map = new HashMap<Parameter, String>();
        map.put(Parameter.CATEGORY, CATEGORY.name());
        map.put(Parameter.COUNTRY, COUNTRY);
        map.put(Parameter.SALARY_FROM, String.valueOf(SALARY_FROM));
        map.put(Parameter.SALARY_TO, String.valueOf(SALARY_TO));
        map.put(Parameter.GRADE, GRADE.name());
        map.put(Parameter.EMPLOYMENT, EMPLOYMENT_TYPE.name());
        map.put(Parameter.ENGLISH_LEVEL, ENGLISH_LEVEL.name());

        when(vacancyRepository.findAll()).thenReturn(vacancies);

        //act
        final var actual = vacancyService.getAll(map);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateVacancy_happyPath() {
        //arrange
        final var id = 1L;
        final var vacancy = Vacancy.builder()
                .id(id)
                .name("Junior Java Developer")
                .grade(GRADE)
                .build();
        final var updatedVacancy = vacancy.withName("Python Developer").withGrade(Grade.SENIOR);
        final var expected = updatedVacancy.withId(id);

        when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
        when(vacancyRepository.save(expected)).thenReturn(expected);

        //act
        final var actual = vacancyService.updateVacancy(updatedVacancy, id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteVacancyById_happyPath() {
        //arrange
        final var id = 1L;

        //act
        vacancyService.deleteVacancy(id);

        //assert
        verify(vacancyRepository).deleteById(id);
    }

}
