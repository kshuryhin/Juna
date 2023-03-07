package ua.pp.juna.vacanciesservice.services.vacancies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import ua.pp.juna.vacanciesservice.repo.vacancies.SkillsRepository;
import ua.pp.juna.vacanciesservice.service.vacancies.SkillsService;
import ua.pp.juna.vacanciesservice.service.vacancies.SkillsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SkillsServiceImplTest {
    private SkillsService skillsService;

    private static final Skill SKILL = Skill.builder()
            .id(1L)
            .name("docker")
            .build();
    @Mock
    private SkillsRepository skillsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.skillsService = new SkillsServiceImpl(skillsRepository);
    }

    @Test
    void saveSkill_happyPath() {
        //arrange
        when(skillsRepository.save(SKILL)).thenReturn(SKILL);

        //act
        final var actual = skillsService.saveSkill(SKILL);

        //assert
        assertThat(actual).isEqualTo(SKILL);
    }

    @Test
    void getAllSkills_happyPath() {
        //arrange
        final var expected = List.of(
                SKILL,
                Skill.builder().build()
        );

        when(skillsRepository.findAll()).thenReturn(expected);

        //act
        final var actual = skillsService.getAllSkills();

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getSkillById_happyPath() {
        //arrange
        final var id = 1L;

        when(skillsRepository.findById(id)).thenReturn(Optional.of(SKILL));

        //act
        final var actual = skillsService.getSkillById(id);

        //assert
        assertThat(actual).isEqualTo(SKILL);
    }

    @Test
    void updateSkill() {
        //arrange
        final var id = 1L;
        final var updatedSkill = SKILL.withName("Devops");

        when(skillsRepository.findById(id)).thenReturn(Optional.of(SKILL));
        when(skillsRepository.save(updatedSkill)).thenReturn(updatedSkill);

        //act
        final var actual = skillsService.updateSkill(updatedSkill, id);

        //assert
        assertThat(actual).isEqualTo(updatedSkill);
    }

    @Test
    void deleteSkill() {
        //arrange
        final var id = 1L;

        //act
        skillsService.deleteSkill(id);

        //assert
        verify(skillsRepository).deleteById(id);
    }
}