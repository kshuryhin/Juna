package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
