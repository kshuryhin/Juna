package ua.pp.juna.vacanciesservice.controller.vacancies;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.domain.vacancies.Vacancy;
import ua.pp.juna.vacanciesservice.service.vacancies.VacancyService;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8085", "http://localhost:4200"}, allowedHeaders = {"Authorization","Origin"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> saveVacancy(@RequestBody Vacancy vacancy,
                                               @RequestParam(name = "skills") List<Long> skills,
                                               @RequestParam(name = "employer") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.createVacancy(vacancy, id, skills));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Vacancy>> getAll(
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(required = false, name = "country") String country,
            @RequestParam(required = false, name = "salaryFrom") String salaryFrom,
            @RequestParam(required = false, name = "salaryTo") String salaryTo,
            @RequestParam(required = false, name = "grade") String grade,
            @RequestParam(required = false, name = "employmentTyoe") String employmentType,
            @RequestParam(required = false, name = "englishLevel") String englishLevel
            ) {


        final var map = new HashMap<Parameter, String>();
        map.put(Parameter.CATEGORY, category);
        map.put(Parameter.COUNTRY, country);
        map.put(Parameter.SALARY_FROM, salaryFrom);
        map.put(Parameter.SALARY_TO, salaryTo);
        map.put(Parameter.GRADE, grade);
        map.put(Parameter.EMPLOYMENT, employmentType);
        map.put(Parameter.ENGLISH_LEVEL, englishLevel);

        return ResponseEntity.ok(vacancyService.getAll(map));
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
