package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.service.MentorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorController {

    private final MentorService mentorService;

    @PostMapping("")
    public ResponseEntity<Mentor> addMentor(@RequestBody Mentor mentor) {
        return ResponseEntity.ok().body(mentorService.addMentor(mentor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mentorService.getMentorById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Mentor>> getAllMentors() {
        return ResponseEntity.ok().body(mentorService.getAllMentors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMentor(@PathVariable Long id) {
        return ResponseEntity.ok().body(mentorService.deleteMentor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateMentor(@PathVariable Long id, @RequestBody Mentor mentor) {
        Mentor result = mentorService.updateMentor(mentor, id);
        if (mentor == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

}
