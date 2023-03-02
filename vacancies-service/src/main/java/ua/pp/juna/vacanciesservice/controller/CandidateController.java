package ua.pp.juna.vacanciesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.domain.Candidate;
import ua.pp.juna.vacanciesservice.service.CandidateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;

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

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate, @PathVariable Long id) {
        return ResponseEntity.ok().body(candidateService.updateCandidate(candidate, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok().build();
    }
}
