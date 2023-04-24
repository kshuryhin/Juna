package ua.pp.juna.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.services.CandidateAnalyticsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/analytics/candidates")
public class CandidateAnalyticsController {
    private final CandidateAnalyticsService candidateAnalyticsService;

    @GetMapping
    public List<CandidateAnalytics> getAllCandidateAnalytics() {
        return candidateAnalyticsService.getAllCandidateAnalytics();
    }

    @GetMapping("/{id}")
    public CandidateAnalytics getCandidateAnalyticsById(@PathVariable(value = "id") String id) {
        return candidateAnalyticsService.getCandidateAnalyticsById(id);
    }

    @GetMapping("/origin/{email}")
    public CandidateAnalytics getCandidateAnalyticsByOriginId(@PathVariable(value = "email") String email) {
        return candidateAnalyticsService.getCandidateAnalyticsByEmail(email);
    }

    @PostMapping
    public CandidateAnalytics createCandidateAnalytics(@RequestBody CandidateAnalytics candidateAnalytics) {
        return candidateAnalyticsService.createCandidateAnalytics(candidateAnalytics);
    }

    @PutMapping("/{email}")
    public CandidateAnalytics updateCandidateAnalytics(@PathVariable(value = "email") String email, @RequestBody CandidateAnalytics candidateAnalytics) {
        return candidateAnalyticsService.updateVacancyStats(email, candidateAnalytics);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidateAnalytics(@PathVariable(value = "id") String id) {
        candidateAnalyticsService.deleteCandidateAnalytics(id);
    }
}
