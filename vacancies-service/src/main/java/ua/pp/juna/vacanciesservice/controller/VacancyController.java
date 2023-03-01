package ua.pp.juna.vacanciesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.domain.Vacancy;
import ua.pp.juna.vacanciesservice.service.VacancyService;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping("")
    public ResponseEntity<Vacancy> saveVacancy(@RequestBody Vacancy vacancy) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.createVacancy(vacancy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }

    @GetMapping("")
    public ResponseEntity<Collection<Vacancy>> getAll() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@RequestBody Vacancy vacancy, @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(vacancyService.updateVacancy(vacancy, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable(name = "id") Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.ok().build();
    }
}
