package ua.pp.juna.vacanciesservice.repo.vacancies;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;

public interface SkillsRepository extends JpaRepository<Skill, Long> {
}
