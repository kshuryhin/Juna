package ua.pp.juna.mentorservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    @Override
    public Student addStudent(final Student student) {
        log.info("Adding student with id {}", student.getId());
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(final Long id) {
        log.info("Getting student with id {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        log.info("Getting all students");
        return studentRepository.findAll();
    }

    @Override
    public boolean deleteStudent(final Long id) {
        log.info("Deleting student with id {}", id);
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Student updateStudent(final Student student, final Long id) {
        log.info("Updating student with id {}", id);
        final Student updated = studentRepository.findById(id).orElse(null);
        if (updated == null)
            return null;

        updated.setFirstName(student.getFirstName());
        updated.setLastName(student.getLastName());
        updated.setEmail(student.getEmail());

        return studentRepository.save(updated);
    }

    @Override
    public Course subscribeOnCourse(final Long studentId, final Long courseId) {
        final Course course = courseRepository.findById(courseId).orElse(null);
        final Student student = studentRepository.findById(studentId).orElse(null);
        if (course == null || student == null)
            return null;

        course.getStudents().add(student);

        return courseRepository.save(course);

    }


}
