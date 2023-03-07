package ua.pp.juna.vacanciesservice.service.vacancies;

import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import java.util.List;

public interface SkillsService {
    Skill saveSkill(Skill skill);

    List<Skill> getAllSkills();

    Skill getSkillById(Long id);

    Skill updateSkill(Skill skill, Long id);

    void deleteSkill(Long id);
}
