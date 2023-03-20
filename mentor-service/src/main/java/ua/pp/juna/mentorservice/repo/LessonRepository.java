package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
