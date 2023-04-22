package ua.pp.juna.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.pp.juna.controllers.request.CandidateRequest;
import ua.pp.juna.services.cv.PdfGenerator;

import java.io.ByteArrayInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.pp.juna.Constants.CANDIDATE_REQUEST;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CvGeneratorController.class)
public class CvGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PdfGenerator pdfGenerator;

    @Test
    void generateCV() throws Exception {
        // Initialize sample data for testing
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("dummy pdf content".getBytes());
        when(pdfGenerator.generateCandidateCV(any(CandidateRequest.class))).thenReturn(byteArrayInputStream);

        mockMvc.perform(post("/api/v1/cv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CANDIDATE_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=candidate_cv.pdf"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));

        verify(pdfGenerator, times(1)).generateCandidateCV(any(CandidateRequest.class));
    }
}
