package ua.pp.juna.vacanciesservice.repo.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByUserDetailsEmail(String email);
}
