package ua.pp.juna.vacanciesservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.Candidate;
import ua.pp.juna.vacanciesservice.repo.CandidateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{
    private final CandidateRepository candidateRepository;
    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate updateCandidate(Candidate candidate, Long id) {
        final Candidate updatedCandidate = getCandidateById(id)
                .withCategories(candidate.getCategories())
                .withCity(candidate.getCity())
                .withPosition(candidate.getPosition())
                .withSummary(candidate.getSummary())
                .withEnglishLevel(candidate.getEnglishLevel())
                .withSalaryExpectations(candidate.getSalaryExpectations())
                .withYearsOfExperience(candidate.getYearsOfExperience());

        return saveCandidate(updatedCandidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
