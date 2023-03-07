package ua.pp.juna.vacanciesservice.controller.vacancies;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.domain.vacancies.Skill;
import ua.pp.juna.vacanciesservice.service.vacancies.SkillsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class SkillsController {
    private final SkillsService skillsService;

    @PostMapping
    public ResponseEntity<Skill> saveSkill(@RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillsService.saveSkill(skill));
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAll() {
        return ResponseEntity.ok(skillsService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(skillsService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill, @PathVariable Long id) {
        return ResponseEntity.ok(skillsService.updateSkill(skill, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillsService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }
}
