package ua.pp.juna.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.pp.juna.domain.VacancyAnalytics;
import ua.pp.juna.repositories.VacancyAnalyticsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.pp.juna.Constants.VACANCY_ANALYTICS;

@ExtendWith(MockitoExtension.class)
public class VacancyAnalyticsServiceTest {

    @InjectMocks
    private VacancyAnalyticsService vacancyAnalyticsService;

    @Mock
    private VacancyAnalyticsRepository vacancyStatsRepository;

    private List<VacancyAnalytics> vacancyStatsList;

    @BeforeEach
    void setUp() {
        // Initialize sample data for testing
        VacancyAnalytics vacancyStats2 = VacancyAnalytics.builder()
                .id(UUID.randomUUID().toString())
                .name("JS Developer")
                .country("Germany")
                .salaryFrom(1320)
                .salaryTo(2930)
                .grade("MIDDLE")
                .employmentType("REMOTE")
                .englishLevel("PRE_INTERMEDIATE")
                .category("JS")
                .views(69)
                .savings(0)
                .applications(21)
                .originVacancyId(6L)
                .build();
        vacancyStatsList = Arrays.asList(VACANCY_ANALYTICS, vacancyStats2);
    }

    @Test
    void getAllVacancyStats() {
        when(vacancyStatsRepository.findAll()).thenReturn(vacancyStatsList);

        List<VacancyAnalytics> result = vacancyAnalyticsService.getAllVacancyStats();
        assertThat(result).isEqualTo(vacancyStatsList);
        verify(vacancyStatsRepository, times(1)).findAll();
    }

    @Test
    void getVacancyStatsById() {
        VacancyAnalytics vacancyStats = vacancyStatsList.get(0);
        when(vacancyStatsRepository.findById(vacancyStats.getId())).thenReturn(Optional.of(vacancyStats));

        VacancyAnalytics result = vacancyAnalyticsService.getVacancyStatsById(vacancyStats.getId());
        assertThat(result).isEqualTo(vacancyStats);
        verify(vacancyStatsRepository, times(1)).findById(vacancyStats.getId());
    }

    @Test
    void getVacancyAnalyticsByOriginId() {
        VacancyAnalytics vacancyStats = vacancyStatsList.get(0);
        when(vacancyStatsRepository.findByOriginVacancyId(vacancyStats.getOriginVacancyId())).thenReturn(vacancyStats);

        VacancyAnalytics result = vacancyAnalyticsService.getVacancyAnalyticsByOriginId(vacancyStats.getOriginVacancyId());
        assertThat(result).isEqualTo(vacancyStats);
        verify(vacancyStatsRepository, times(1)).findByOriginVacancyId(vacancyStats.getOriginVacancyId());
    }

    @Test
    void createVacancyStats() {
        VacancyAnalytics vacancyStats = vacancyStatsList.get(0);
        when(vacancyStatsRepository.save(any(VacancyAnalytics.class))).thenReturn(vacancyStats);

        VacancyAnalytics result = vacancyAnalyticsService.createVacancyStats(vacancyStats);
        assertThat(result).isEqualTo(vacancyStats);
        verify(vacancyStatsRepository, times(1)).save(any(VacancyAnalytics.class));
    }

    @Test
    void updateVacancyStats() {
        VacancyAnalytics vacancyStats = vacancyStatsList.get(0);
        VacancyAnalytics updatedVacancyStats = VacancyAnalytics.builder()
                .name("JS Developer")
                .country("Belgium")
                .salaryFrom(1320)
                .salaryTo(2930)
                .grade("MIDDLE")
                .employmentType("REMOTE")
                .englishLevel("PRE_INTERMEDIATE")
                .category("JS")
                .views(69)
                .savings(0)
                .applications(21)
                .originVacancyId(6L)
                .build();

        when(vacancyStatsRepository.findById(vacancyStats.getId())).thenReturn(Optional.of(vacancyStats));
        when(vacancyStatsRepository.save(any(VacancyAnalytics.class))).thenReturn(updatedVacancyStats);

        VacancyAnalytics result = vacancyAnalyticsService.updateVacancyStats(vacancyStats.getId(), updatedVacancyStats);
        assertThat(result).isEqualTo(updatedVacancyStats);
        verify(vacancyStatsRepository, times(1)).findById(vacancyStats.getId());
        verify(vacancyStatsRepository, times(1)).save(any(VacancyAnalytics.class));
    }

    @Test
    void deleteVacancyStats() {
        VacancyAnalytics vacancyStats = vacancyStatsList.get(0);
        when(vacancyStatsRepository.findById(vacancyStats.getId())).thenReturn(Optional.of(vacancyStats));
        doNothing().when(vacancyStatsRepository).delete(any(VacancyAnalytics.class));

        vacancyAnalyticsService.deleteVacancyStats(vacancyStats.getId());

        verify(vacancyStatsRepository, times(1)).findById(vacancyStats.getId());
        verify(vacancyStatsRepository, times(1)).delete(any(VacancyAnalytics.class));
    }
}

