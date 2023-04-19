package ua.pp.juna.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.juna.domain.VacancyAnalytics;
import ua.pp.juna.services.VacancyAnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/analytics/vacancies")
public class VacancyAnalyticsController {
    private final VacancyAnalyticsService vacancyStatsService;

    @GetMapping
    public List<VacancyAnalytics> getAllVacancyStats() {
        return vacancyStatsService.getAllVacancyStats();
    }

    @GetMapping("/{id}")
    public VacancyAnalytics getVacancyStatsById(@PathVariable(value = "id") String id) {
        return vacancyStatsService.getVacancyStatsById(id);
    }

    @GetMapping("/origin/{id}")
    public VacancyAnalytics getVacancyAnalyticsByOriginId(@PathVariable(value = "id") Long id) {
        return vacancyStatsService.getVacancyAnalyticsByOriginId(id);
    }

    @PostMapping
    public VacancyAnalytics createVacancyStats(@RequestBody VacancyAnalytics vacancyStats) {
        return vacancyStatsService.createVacancyStats(vacancyStats);
    }

    @PutMapping("/{id}")
    public VacancyAnalytics updateVacancyStats(@PathVariable(value = "id") String id, @RequestBody VacancyAnalytics vacancyStatsDetails) {
        return vacancyStatsService.updateVacancyStats(id, vacancyStatsDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteVacancyStats(@PathVariable(value = "id") String id) {
        vacancyStatsService.deleteVacancyStats(id);
    }
}
