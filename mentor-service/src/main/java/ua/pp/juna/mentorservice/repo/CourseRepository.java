package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.model.Student;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByStudents(Student student);
    List<Course> findAllByMentor(Mentor mentor);
}
