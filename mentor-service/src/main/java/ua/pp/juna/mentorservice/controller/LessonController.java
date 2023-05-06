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
@CrossOrigin(origins = {"http://localhost:8085", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {
    private final LessonService lessonService;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;


    @PostMapping("/{courseId}")
    public ResponseEntity<Course> addLesson(@RequestBody Lesson lesson, @PathVariable Long courseId) {
        final Course result = lessonService.addLesson(lesson, courseId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        final Lesson result = lessonService.getLessonById(id);
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

    @GetMapping("/course/{courseId}/lesson/{orderInCourse}")
    public ResponseEntity<Lesson> getLessonByOrder(@PathVariable(name = "courseId") Long courseId,
                                                   @PathVariable(name = "orderInCourse") Integer orderInCourse) {

        return ResponseEntity.ok().body(lessonService.getLessonByOrderInCourse(courseId, orderInCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        final boolean isDeleted = lessonService.deleteLesson(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson, @PathVariable Long id) {
        final Lesson result = lessonService.updateLesson(lesson, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
