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
import ua.pp.juna.domain.CandidateAnalytics;
import ua.pp.juna.services.CandidateAnalyticsService;

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
import static ua.pp.juna.Constants.CANDIDATE_ANALYTICS;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CandidateAnalyticsController.class)
public class CandidateAnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateAnalyticsService candidateAnalyticsService;

    private List<CandidateAnalytics> candidateAnalyticsList;

    @BeforeEach
    void setUp() {
        // Initialize sample data for testing
        CandidateAnalytics candidateAnalytics2 = CandidateAnalytics.builder()
                .id(UUID.randomUUID().toString())
                .email("biba0202@gmail.com")
                .position("Python Developer")
                .isActive(true)
                .salaryExpectations(1500)
                .grade("MIDDLE")
                .country("Germany")
                .category("PYTHON")
                .englishLevel("UPPER_INTERMEDIATE")
                .employmentType("REMOTE")
                .build();
        candidateAnalyticsList = Arrays.asList(CANDIDATE_ANALYTICS, candidateAnalytics2);
    }

    @Test
    void getAllCandidateAnalytics() throws Exception {
        when(candidateAnalyticsService.getAllCandidateAnalytics()).thenReturn(candidateAnalyticsList);

        mockMvc.perform(get("/api/v1/analytics/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(candidateAnalyticsService, times(1)).getAllCandidateAnalytics();
    }

    @Test
    void getCandidateAnalyticsById() throws Exception {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsService.getCandidateAnalyticsById(candidateAnalytics.getId())).thenReturn(candidateAnalytics);

        mockMvc.perform(get("/api/v1/analytics/candidates/{id}", candidateAnalytics.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(candidateAnalytics.getId())));

        verify(candidateAnalyticsService, times(1)).getCandidateAnalyticsById(candidateAnalytics.getId());
    }

    @Test
    void getCandidateAnalyticsByOriginId() throws Exception {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsService.getCandidateAnalyticsByEmail(candidateAnalytics.getEmail())).thenReturn(candidateAnalytics);

        mockMvc.perform(get("/api/v1/analytics/candidates/origin/{email}", candidateAnalytics.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(candidateAnalytics.getEmail())));

        verify(candidateAnalyticsService, times(1)).getCandidateAnalyticsByEmail(candidateAnalytics.getEmail());
    }

    @Test
    void createCandidateAnalytics() throws Exception {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsService.createCandidateAnalytics(any(CandidateAnalytics.class))).thenReturn(candidateAnalytics);

        mockMvc.perform(post("/api/v1/analytics/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateAnalytics)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(candidateAnalytics.getId())));

        verify(candidateAnalyticsService, times(1)).createCandidateAnalytics(any(CandidateAnalytics.class));
    }

    @Test
    void updateCandidateAnalytics() throws Exception {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        when(candidateAnalyticsService.updateVacancyStats(eq(candidateAnalytics.getEmail()), any(CandidateAnalytics.class))).thenReturn(candidateAnalytics);

        mockMvc.perform(put("/api/v1/analytics/candidates/{email}", candidateAnalytics.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateAnalytics)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(candidateAnalytics.getId())));

        verify(candidateAnalyticsService, times(1)).updateVacancyStats(eq(candidateAnalytics.getEmail()), any(CandidateAnalytics.class));
    }

    @Test
    void deleteCandidateAnalytics() throws Exception {
        CandidateAnalytics candidateAnalytics = candidateAnalyticsList.get(0);
        doNothing().when(candidateAnalyticsService).deleteCandidateAnalytics(candidateAnalytics.getId());

        mockMvc.perform(delete("/api/v1/analytics/candidates/{id}", candidateAnalytics.getId()))
                .andExpect(status().isOk());

        verify(candidateAnalyticsService, times(1)).deleteCandidateAnalytics(candidateAnalytics.getId());
    }
    }