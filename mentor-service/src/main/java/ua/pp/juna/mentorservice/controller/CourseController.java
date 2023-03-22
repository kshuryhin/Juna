package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.MentorRepository;
import ua.pp.juna.mentorservice.repo.StudentRepository;
import ua.pp.juna.mentorservice.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;

    @PostMapping("/{mentorId}")
    public ResponseEntity<Course> addCourse(@RequestBody Course course, @PathVariable Long mentorId) {
        return ResponseEntity.ok().body(courseService.addCourse(course, mentorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course result =  courseService.getCourseById(id);
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
        boolean isDeleted = courseService.deleteCourse(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId) {

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(courseRepository.findAllByStudents(student));
        }
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<Course>> getCoursesByMentor(@PathVariable Long mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId).orElse(null);
        if (mentor == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(courseRepository.findAllByMentor(mentor));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable Long id){
        Course result = courseService.updateCourse(course, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
