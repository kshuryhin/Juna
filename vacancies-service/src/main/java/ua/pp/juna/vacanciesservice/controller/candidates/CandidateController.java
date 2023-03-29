package ua.pp.juna.vacanciesservice.controller.candidates;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.utils.PhotoSaver;
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
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return ResponseEntity.ok().body(candidateService.getAllCandidates());
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
        final var fileName = photoSaver.savePhoto(file);
        return ResponseEntity.ok(fileName);
    }

}
