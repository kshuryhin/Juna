package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    boolean deleteStudent(Long id);
    Student updateStudent(Student student, Long id);
    Course subscribeOnCourse(Long studentId, Long courseId);
}
