package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.service.StudentService;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.addStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.deleteStudent(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        Student result = studentService.updateStudent(student, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @PutMapping("/{courseId}/{studentId}")
    public void subscribe(@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "studentId") Long studentId) {
        studentService.subscribeOnCourse(studentId, courseId);
    }

}
