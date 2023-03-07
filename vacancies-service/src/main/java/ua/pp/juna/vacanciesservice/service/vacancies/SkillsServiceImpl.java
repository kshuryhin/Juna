package ua.pp.juna.vacanciesservice.service.vacancies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import ua.pp.juna.vacanciesservice.repo.vacancies.SkillsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillsServiceImpl implements SkillsService{
    private final SkillsRepository skillsRepository;

    @Override
    public Skill saveSkill(final Skill skill) {
        log.info("Saving skill {}", skill);
        return skillsRepository.save(skill);
    }

    @Override
    public List<Skill> getAllSkills() {
        log.info("Fetching all skills");
        return skillsRepository.findAll();
    }

    @Override
    public Skill getSkillById(final Long id) {
        log.info("Fetching skill by id {}", id);
        return skillsRepository.findById(id).orElse(null);
    }

    @Override
    public Skill updateSkill(final Skill skill, final Long id) {
        log.info("Updating skill with id {} on {}", id, skill);
        final var skillToUpdate = skillsRepository.findById(id).orElse(null)
                .withName(skill.getName());

        return skillsRepository.save(skillToUpdate);
    }

    @Override
    public void deleteSkill(Long id) {
        log.info("Deleting skill with id {}", id);
        skillsRepository.deleteById(id);
    }
}
