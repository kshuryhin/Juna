package ua.pp.juna.vacanciesservice.controller.employers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.service.employers.EmployerService;
import ua.pp.juna.vacanciesservice.utils.PhotoSaver;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employers")
public class EmployerController {
    private final EmployerService employerService;

    private final PhotoSaver photoSaver;

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

    @GetMapping("/email/{email}")
    public ResponseEntity<Employer> getEmployerByEmail(@PathVariable String email){
        return ResponseEntity.ok(employerService.getEmployerByEmail(email));
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

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        final var fileName = photoSaver.savePhoto(file, "employers");
        return ResponseEntity.ok(fileName);
    }
}
