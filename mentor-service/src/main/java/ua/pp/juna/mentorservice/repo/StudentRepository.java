package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
