package ua.pp.juna.vacanciesservice.service;

import ua.pp.juna.vacanciesservice.domain.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate saveCandidate(Candidate candidate);

    Candidate getCandidateById(Long id);

    List<Candidate> getAllCandidates();

    Candidate updateCandidate(Candidate candidate, Long id);

    void deleteCandidate(Long id);

}
