package ua.pp.juna.services.cv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.juna.controllers.request.CandidateRequest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfGeneratorTest {
    private PdfGenerator pdfGenerator;
    private CandidateRequest candidateRequest;

    @BeforeEach
    void setUp() {
        pdfGenerator = new PdfGenerator();

        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Spring Boot");

        candidateRequest = new CandidateRequest("Java Developer", true, 60000, "Middle", "USA", "New York", skills, "John", "Doe", "john.doe@example.com", "Software Development", "B2", "Full-time", "5 years", "123-456-7890", "Pet project description", "kostya.jpg", "https://www.linkedin.com/in/johndoe", "https://t.me/johndoe", "https://github.com/johndoe");
    }

    @Test
    void generateCandidateCV() {
        ByteArrayInputStream result = pdfGenerator.generateCandidateCV(candidateRequest);
        assertThat(result).isNotNull();
    }
}
