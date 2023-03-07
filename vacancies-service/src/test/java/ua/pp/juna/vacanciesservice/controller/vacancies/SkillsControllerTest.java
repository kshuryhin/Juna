package ua.pp.juna.vacanciesservice.controller.vacancies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import ua.pp.juna.vacanciesservice.service.vacancies.SkillsService;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

class SkillsControllerTest {
    private SkillsController skillsController;
    @Mock
    private SkillsService skillsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.skillsController = new SkillsController(skillsService);
    }

    @Test
    void saveSkill_happyPath() {
        //arrange
        final var skill = Skill.builder().id(1L).name("docker").build();
        final var expected = ResponseEntity.status(HttpStatus.CREATED).body(skill);

        when(skillsService.saveSkill(skill)).thenReturn(skill);

        //act
        final var actual = skillsController.saveSkill(skill);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAll_happyPath() {
        //arrange
        final var skills = List.of(
                Skill.builder().id(1L).name("docker").build(),
                Skill.builder().id(2L).name("Cucumber").build()
        );
        final var expected = ResponseEntity.ok().body(skills);

        when(skillsService.getAllSkills()).thenReturn(skills);

        //act
        final var actual = skillsController.getAll();

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getSkillById_happyPath() {
        //arrange
        final var id = 1L;
        final var skill = Skill.builder().id(id).name("docker").build();
        final var expected = ResponseEntity.ok().body(skill);

        when(skillsService.getSkillById(id)).thenReturn(skill);

        //act
        final var actual = skillsController.getSkillById(id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateSkill_happyPath() {
        //arrange
        final var id = 1L;
        final var skill = Skill.builder().id(id).name("docker").build();
        final var expected = ResponseEntity.ok().body(skill);

        when(skillsService.updateSkill(skill, id)).thenReturn(skill);

        //act
        final var actual = skillsController.updateSkill(skill, id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteSkill_happyPath() {
        //arrange
        final var id = 1L;
        final var expected = ResponseEntity.ok().build();

        //act
        final var actual = skillsController.deleteSkill(id);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}