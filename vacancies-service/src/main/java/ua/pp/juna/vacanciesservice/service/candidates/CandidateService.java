package ua.pp.juna.vacanciesservice.service.candidates;

import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate saveCandidate(Candidate candidate);

    Candidate getCandidateById(Long id);

    Candidate getCandidateByEmail(String email);

    List<Candidate> getAllCandidates();

    Candidate updateCandidate(Candidate candidate, Long id);

    Candidate patchCandidate(String email, String newPassword);

    void deleteCandidate(Long id);

}
