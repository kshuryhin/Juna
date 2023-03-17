package ua.pp.juna.vacanciesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.domain.Employer;
import ua.pp.juna.vacanciesservice.service.EmployerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employers")
public class EmployerController {
    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<Employer> saveEmployer(@RequestBody Employer employer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employerService.saveEmployer(employer));
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        return ResponseEntity.ok().body(employerService.getAllEmployers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(employerService.getEmployerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@RequestBody Employer employer, @PathVariable Long id) {
        return ResponseEntity.ok().body(employerService.updateEmployer(employer, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.ok().build();
    }
}
