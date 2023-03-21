package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Lesson;
import ua.pp.juna.mentorservice.service.LessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/{courseId}")
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson, @PathVariable Long courseId) {
        return ResponseEntity.ok().body(lessonService.addLesson(lesson, courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(lessonService.getLessonById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok().body(lessonService.getAllLessons());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return ResponseEntity.ok().body(lessonService.deleteLesson(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson, @PathVariable Long id) {
        Lesson result = lessonService.updateLesson(lesson, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
