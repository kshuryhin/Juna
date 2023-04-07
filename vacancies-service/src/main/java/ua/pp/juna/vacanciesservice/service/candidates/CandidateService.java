package ua.pp.juna.vacanciesservice.service.candidates;

import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.util.List;
import java.util.Map;

public interface CandidateService {
    Candidate saveCandidate(Candidate candidate);

    Candidate getCandidateById(Long id);

    Candidate getCandidateByEmail(String email);

    List<Candidate> getAllCandidates(Map<Parameter, String> params);

    Candidate updateCandidate(Candidate candidate, Long id);
    void deleteCandidate(Long id);

}
