package ua.pp.juna.vacanciesservice.controller.candidates;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.utils.Parameter;
import ua.pp.juna.vacanciesservice.utils.PhotoSaver;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final PhotoSaver photoSaver;

    @PostMapping
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.saveCandidate(candidate));
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates(
            @RequestParam(required = false, name = "country") String country,
            @RequestParam(required = false, name = "englishLevel") String englishLevel,
            @RequestParam(required = false, name = "employmentType") String employmentType,
            @RequestParam(required = false, name = "grade") String grade,
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(required = false, name = "salaryExpectations") String salaryExpectations
    ) {
        final var map = new HashMap<Parameter, String>();
        map.put(Parameter.CATEGORY, category);
        map.put(Parameter.COUNTRY, country);
        map.put(Parameter.SALARY_EXPECTATIONS, salaryExpectations);
        map.put(Parameter.GRADE, grade);
        map.put(Parameter.EMPLOYMENT, employmentType);
        map.put(Parameter.ENGLISH_LEVEL, englishLevel);
        return ResponseEntity.ok().body(candidateService.getAllCandidates(map));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok().body(candidateService.getCandidateById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Candidate> getCandidateByEmail(@PathVariable String email) {
        return ResponseEntity.ok(candidateService.getCandidateByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate, @PathVariable Long id) {
        return ResponseEntity.ok().body(candidateService.updateCandidate(candidate, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        final var fileName = photoSaver.savePhoto(file, "candidates");
        return ResponseEntity.ok(fileName);
    }
}
