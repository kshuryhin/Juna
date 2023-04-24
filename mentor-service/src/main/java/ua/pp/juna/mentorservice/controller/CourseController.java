package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.StudentRepository;
import ua.pp.juna.mentorservice.service.CourseService;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:8085", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @PostMapping("/{mentorId}")
    public ResponseEntity<Course> addCourse(@RequestBody Course course, @PathVariable Long mentorId) {
        return ResponseEntity.ok().body(courseService.addCourse(course, mentorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        final Course result =  courseService.getCourseById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        final boolean isDeleted = courseService.deleteCourse(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId) {

        final Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(courseRepository.findAllByStudents(student));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable Long id){
        final Course result = courseService.updateCourse(course, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
