package ua.pp.juna.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.repositories.CandidateAnalyticsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.pp.juna.Constants.CANDIDATE_ANALYTICS;

@ExtendWith(MockitoExtension.class)
public class CandidateAnalyticsServiceTest {

    @InjectMocks
    private CandidateAnalyticsService candidateAnalyticsService;

    @Mock
    private CandidateAnalyticsRepository candidateAnalyticsRepository;

    private List<CandidateAnalytics> candidateAnalyticsList;

    @BeforeEach
    void setUp() {
        // Initialize sample data for testing
        CandidateAnalytics candidateAnalytics2 = CandidateAnalytics.builder()
                .id(UUID.randomUUID().toString())
                .email("ksurygin5@gmail.com")
                .position("Java Developer")
                .isActive(true)
                .salaryExpectations(2000)
                .grade("JUNIOR")
                .country("Ukraine")
                .category("JAVA")
                .englishLevel("ADVANCED")
                .employmentType("ALL")
                .build();
        candidateAnalyticsList = Arrays.asList(CANDIDATE_ANALYTICS, candidateAnalytics2);
    }

    @Test
    void getAllCandidateAnalytics() {
        when(candidateAnalyticsRepository.findAll()).thenReturn(candidateAnalyticsList);

        List<CandidateAnalytics> result = candidateAnalyticsService.getAllCandidateAnalytics();
        assertThat(result).isEqualTo(candidateAnalyticsList);
        verify(candidateAnalyticsRepository, times(1)).findAll();
    }

    @Test
    void getCandidateAnalyticsById() {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsRepository.findById(candidateAnalytics.getId())).thenReturn(Optional.of(candidateAnalytics));

        CandidateAnalytics result = candidateAnalyticsService.getCandidateAnalyticsById(candidateAnalytics.getId());
        assertThat(result).isEqualTo(candidateAnalytics);
        verify(candidateAnalyticsRepository, times(1)).findById(candidateAnalytics.getId());
    }

    @Test
    void getCandidateAnalyticsByEmail() {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsRepository.findByEmail(candidateAnalytics.getEmail())).thenReturn(candidateAnalytics);

        CandidateAnalytics result = candidateAnalyticsService.getCandidateAnalyticsByEmail(candidateAnalytics.getEmail());
        assertThat(result).isEqualTo(candidateAnalytics);
        verify(candidateAnalyticsRepository, times(1)).findByEmail(candidateAnalytics.getEmail());
    }

    @Test
    void createCandidateAnalytics() {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsRepository.save(any(CandidateAnalytics.class))).thenReturn(candidateAnalytics);

        CandidateAnalytics result = candidateAnalyticsService.createCandidateAnalytics(candidateAnalytics);
        assertThat(result).isEqualTo(candidateAnalytics);
        verify(candidateAnalyticsRepository, times(1)).save(any(CandidateAnalytics.class));
    }

    @Test
    void updateVacancyStats() {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        CandidateAnalytics updatedCandidateAnalytics = CandidateAnalytics.builder()
                .email("ksurygin5@gmail.com")
                .position("Java Developer")
                .isActive(true)
                .salaryExpectations(2000)
                .grade("JUNIOR")
                .country("Belgium")
                .category("JAVA")
                .englishLevel("ADVANCED")
                .employmentType("ALL")
                .build();

        when(candidateAnalyticsRepository.findByEmail(candidateAnalytics.getEmail())).thenReturn(candidateAnalytics);
        when(candidateAnalyticsRepository.save(any(CandidateAnalytics.class))).thenReturn(updatedCandidateAnalytics);

        CandidateAnalytics result = candidateAnalyticsService.updateVacancyStats(candidateAnalytics.getEmail(), updatedCandidateAnalytics);
        assertThat(result).isEqualTo(updatedCandidateAnalytics);
        verify(candidateAnalyticsRepository, times(1)).findByEmail(candidateAnalytics.getEmail());
        verify(candidateAnalyticsRepository, times(1)).save(any(CandidateAnalytics.class));
    }

    @Test
    void deleteCandidateAnalytics() {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsRepository.findById(candidateAnalytics.getId())).thenReturn(Optional.of(candidateAnalytics));
        doNothing().when(candidateAnalyticsRepository).delete(any(CandidateAnalytics.class));

        candidateAnalyticsService.deleteCandidateAnalytics(candidateAnalytics.getId());

        verify(candidateAnalyticsRepository, times(1)).findById(candidateAnalytics.getId());
        verify(candidateAnalyticsRepository, times(1)).delete(any(CandidateAnalytics.class));
    }
}
