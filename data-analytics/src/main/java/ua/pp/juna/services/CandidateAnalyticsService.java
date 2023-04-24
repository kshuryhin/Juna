package ua.pp.juna.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.repositories.CandidateAnalyticsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateAnalyticsService {

    private final CandidateAnalyticsRepository candidateAnalyticsRepository;

    public List<CandidateAnalytics> getAllCandidateAnalytics() {
        return candidateAnalyticsRepository.findAll();
    }

    public CandidateAnalytics getCandidateAnalyticsById(String id) {
        return candidateAnalyticsRepository.findById(id).orElseThrow();
    }

    public CandidateAnalytics getCandidateAnalyticsByEmail(final String email){
        return candidateAnalyticsRepository.findByEmail(email);
    }

    public CandidateAnalytics createCandidateAnalytics(CandidateAnalytics candidateAnalytics) {
        return candidateAnalyticsRepository.save(candidateAnalytics);
    }

    public CandidateAnalytics updateVacancyStats(String email, CandidateAnalytics candidateAnalyticsDetails) {
        CandidateAnalytics candidateAnalytics = getCandidateAnalyticsByEmail(email);

        candidateAnalytics.setPosition(candidateAnalyticsDetails.getPosition());
        candidateAnalytics.setCountry(candidateAnalyticsDetails.getCountry());
        candidateAnalytics.setSalaryExpectations(candidateAnalyticsDetails.getSalaryExpectations());
        candidateAnalytics.setGrade(candidateAnalyticsDetails.getGrade());
        candidateAnalytics.setEmploymentType(candidateAnalyticsDetails.getEmploymentType());
        candidateAnalytics.setEnglishLevel(candidateAnalyticsDetails.getEnglishLevel());
        candidateAnalytics.setCategory(candidateAnalyticsDetails.getCategory());
        candidateAnalytics.setIsActive(candidateAnalyticsDetails.getIsActive());

        return candidateAnalyticsRepository.save(candidateAnalytics);
    }

    public void deleteCandidateAnalytics(String id) {
        CandidateAnalytics vacancyStats = getCandidateAnalyticsById(id);
        candidateAnalyticsRepository.delete(vacancyStats);
    }
}
