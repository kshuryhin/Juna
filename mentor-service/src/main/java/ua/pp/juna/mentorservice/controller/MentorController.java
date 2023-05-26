package ua.pp.juna.mentorservice.controller;


import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.juna.mentorservice.model.CreateRequest;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.repo.MentorRepository;
import ua.pp.juna.mentorservice.service.MentorService;
import ua.pp.juna.mentorservice.utils.PhotoSaver;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:8085", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentors")
public class MentorController {

    private final MentorService mentorService;
    private final PhotoSaver photoSaver;
    private final MentorRepository mentorRepository;

    @PostMapping("")
    public ResponseEntity<Mentor> addMentor(@RequestBody CreateRequest createRequest) {
        Mentor mentor = createRequest.getUserDetails();
        return ResponseEntity.ok().body(mentorService.addMentor(mentor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        final Mentor result = mentorService.getMentorById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Mentor> getMentorByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(mentorRepository.findByEmail(email));
    }

    @GetMapping("")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok().body(mentorService.getAllMentors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMentor(@PathVariable Long id) {
        final boolean isDeleted = mentorService.deleteMentor(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateMentor(@PathVariable Long id, @RequestBody Mentor mentor) {
        final Mentor result = mentorService.updateMentor(mentor, id);
        if (mentor == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        final var fileName = photoSaver.savePhoto(file, "mentors");
        return ResponseEntity.ok(fileName);
    }


}
