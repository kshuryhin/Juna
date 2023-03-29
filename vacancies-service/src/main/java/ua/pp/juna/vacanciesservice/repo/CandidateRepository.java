package ua.pp.juna.vacanciesservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByUserDetailsEmail(String email);
}
