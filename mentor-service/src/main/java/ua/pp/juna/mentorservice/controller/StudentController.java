package ua.pp.juna.mentorservice.controller;


import com.netflix.discovery.util.EurekaEntityComparators;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.CreateStudentRequest;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.StudentRepository;
import ua.pp.juna.mentorservice.service.StudentService;

import java.nio.file.Path;
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

    @PutMapping("/{courseId}/{studentId}")
    public ResponseEntity<Course> subscribe(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "studentId") Long studentId) {
        final Course course = studentService.subscribeOnCourse(studentId, courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(course);
        }
    }

}
