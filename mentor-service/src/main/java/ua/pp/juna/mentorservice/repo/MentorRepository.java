package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
