package ua.pp.juna.vacanciesservice.controller.vacancies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;
import ua.pp.juna.vacanciesservice.service.vacancies.VacancyService;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class VacancyControllerTest {

    private VacancyController vacancyController;
    @Mock
    private VacancyService vacancyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.vacancyController = new VacancyController(vacancyService);
    }

    @Test
    void saveVacancy_happyPath() {
        //arrange
        final var vacancy = Vacancy.builder().name("Junior Java Developer").build();
        final var saved = vacancy.withEmployer(Employer.builder().build()).withSkills(List.of(Skill.builder().build()));
        final var skillIDs = List.of(1L,2L,3L);
        final var employerId = 1L;
        final var expected = ResponseEntity.status(HttpStatus.CREATED).body(saved);

        when(vacancyService.createVacancy(vacancy, employerId, skillIDs)).thenReturn(saved);

        //act
        final var actual = vacancyController.saveVacancy(vacancy, skillIDs, employerId);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getVacancyById_happyPath() {
        //arrange
        final var id = 1L;
        final var vacancy = Vacancy.builder().id(id).build();
        final var expected = ResponseEntity.ok().body(vacancy);

        when(vacancyService.getVacancyById(id)).thenReturn(vacancy);

        //act
        final var actual = vacancyController.getVacancyById(id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAll_withoutParams_happyPath() {
        //arrange
        final var vacancies = List.of(
                Vacancy.builder().id(1L).build(),
                Vacancy.builder().id(2L).build()
        );
        final var expected = ResponseEntity.ok().body(vacancies);

        when(vacancyService.getAll(any())).thenReturn(vacancies);

        //act
        final var actual = vacancyController.getAll(null, null, null,  null, null, null);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAll_withParams_happyPath() {
        //arrange
        final var vacancies = List.of(
                Vacancy.builder().id(1L).build(),
                Vacancy.builder().id(2L).build()
        );
        final var expected = ResponseEntity.ok().body(vacancies);

        when(vacancyService.getAll(any())).thenReturn(vacancies);

        //act
        final var actual = vacancyController.getAll("Category", "Country", null, null, null, null);

        //assert
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void updateVacancy_happyPath() {
        //arrange
        final var id = 1L;
        final var vacancy = Vacancy.builder().id(id).name("Junior Java Developer").build();
        final var expected = ResponseEntity.ok().body(vacancy.withName("Python developer"));

        when(vacancyService.updateVacancy(vacancy, id)).thenReturn(vacancy.withName("Python developer"));

        //act
        final var actual = vacancyController.updateVacancy(vacancy, id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteVacancy_happyPath() {
        //arrange
        final var id = 1L;
        final var expected = ResponseEntity.ok().build();

        //act
        final var actual = vacancyController.deleteVacancy(id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}