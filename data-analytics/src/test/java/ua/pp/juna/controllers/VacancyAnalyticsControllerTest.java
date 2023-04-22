package ua.pp.juna.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.pp.juna.domain.VacancyAnalytics;
import ua.pp.juna.services.VacancyAnalyticsService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.pp.juna.Constants.VACANCY_ANALYTICS;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VacancyAnalyticsController.class)
public class VacancyAnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VacancyAnalyticsService vacancyStatsService;

    private List<VacancyAnalytics> vacancyAnalyticsList;

    @BeforeEach
    void setUp() {
        // Initialize sample data for testing
        VacancyAnalytics vacancyAnalytics2 = VacancyAnalytics.builder()
                .id(UUID.randomUUID().toString())
                .name("Python Developer")
                .country("Ukraine")
                .salaryFrom(1300)
                .salaryTo(2900)
                .grade("MIDDLE")
                .employmentType("REMOTE")
                .englishLevel("UPPER_INTERMEDIATE")
                .category("PYTHON")
                .views(56)
                .savings(0)
                .applications(13)
                .originVacancyId(2L)
                .build();
        vacancyAnalyticsList = Arrays.asList(VACANCY_ANALYTICS, vacancyAnalytics2);
    }

    @Test
    void getAllVacancyStats() throws Exception {
        when(vacancyStatsService.getAllVacancyStats()).thenReturn(vacancyAnalyticsList);

        mockMvc.perform(get("/api/v1/analytics/vacancies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(vacancyStatsService, times(1)).getAllVacancyStats();
    }

    @Test
    void getVacancyStatsById() throws Exception {
        VacancyAnalytics vacancyAnalytics = vacancyAnalyticsList.get(0);
        when(vacancyStatsService.getVacancyStatsById(vacancyAnalytics.getId())).thenReturn(vacancyAnalytics);

        mockMvc.perform(get("/api/v1/analytics/vacancies/{id}", vacancyAnalytics.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(vacancyAnalytics.getId())));

        verify(vacancyStatsService, times(1)).getVacancyStatsById(vacancyAnalytics.getId());
    }

    @Test
    void getVacancyAnalyticsByOriginId() throws Exception {
        VacancyAnalytics vacancyAnalytics = vacancyAnalyticsList.get(0);
        when(vacancyStatsService.getVacancyAnalyticsByOriginId(vacancyAnalytics.getOriginVacancyId())).thenReturn(vacancyAnalytics);

        mockMvc.perform(get("/api/v1/analytics/vacancies/origin/{id}", vacancyAnalytics.getOriginVacancyId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.originVacancyId", is(vacancyAnalytics.getOriginVacancyId().intValue())));

        verify(vacancyStatsService, times(1)).getVacancyAnalyticsByOriginId(vacancyAnalytics.getOriginVacancyId());
    }

    @Test
    void createVacancyStats() throws Exception {
        VacancyAnalytics vacancyAnalytics = vacancyAnalyticsList.get(0);
        when(vacancyStatsService.createVacancyStats(any(VacancyAnalytics.class))).thenReturn(vacancyAnalytics);

        mockMvc.perform(post("/api/v1/analytics/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyAnalytics)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(vacancyAnalytics.getId())));

        verify(vacancyStatsService, times(1)).createVacancyStats(any(VacancyAnalytics.class));
    }

    @Test
    void updateVacancyStats() throws Exception {
        VacancyAnalytics vacancyAnalytics = vacancyAnalyticsList.get(0);
        when(vacancyStatsService.updateVacancyStats(eq(vacancyAnalytics.getId()), any(VacancyAnalytics.class))).thenReturn(vacancyAnalytics);

        mockMvc.perform(put("/api/v1/analytics/vacancies/{id}", vacancyAnalytics.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyAnalytics)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(vacancyAnalytics.getId())));

        verify(vacancyStatsService, times(1)).updateVacancyStats(eq(vacancyAnalytics.getId()), any(VacancyAnalytics.class));
    }

    @Test
    void deleteVacancyStats() throws Exception {
        VacancyAnalytics vacancyAnalytics = vacancyAnalyticsList.get(0);
        doNothing().when(vacancyStatsService).deleteVacancyStats(vacancyAnalytics.getId());

        mockMvc.perform(delete("/api/v1/analytics/vacancies/{id}", vacancyAnalytics.getId()))
                .andExpect(status().isOk());

        verify(vacancyStatsService, times(1)).deleteVacancyStats(vacancyAnalytics.getId());
    }
}

