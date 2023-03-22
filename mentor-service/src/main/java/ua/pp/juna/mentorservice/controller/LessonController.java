package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Lesson;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.LessonRepository;
import ua.pp.juna.mentorservice.service.LessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @PostMapping("/{courseId}")
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson, @PathVariable Long courseId) {
        Lesson result = lessonService.addLesson(lesson, courseId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson result = lessonService.getLessonById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }

    }

    @GetMapping("")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok().body(lessonService.getAllLessons());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        boolean isDeleted = lessonService.deleteLesson(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(lessonRepository.findAllByCourse(course));
        }
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
