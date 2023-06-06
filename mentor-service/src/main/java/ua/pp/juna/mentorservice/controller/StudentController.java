package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.CreateStudentRequest;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.StudentRepository;
import ua.pp.juna.mentorservice.service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @PostMapping("")
    public ResponseEntity<Student> addStudent(@RequestBody CreateStudentRequest request) {
        Student student = request.getUserDetails();
        return ResponseEntity.ok().body(studentService.addStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        final Student result = studentService.getStudentById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(studentRepository.findByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        final boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        final Student result = studentService.updateStudent(student, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @PutMapping("/course/{courseId}/email/{email}")
    public ResponseEntity<Course> subscribe(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "email") String email) {
        final Course course = studentService.subscribeOnCourse(email, courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(course);
        }
    }

    @PutMapping("/course/{courseId}/like/email/{email}")
    public ResponseEntity<Course> likeCourse(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "email") String email) {
        final Course course = studentService.likeCourse(email, courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(course);
        }
    }

}
