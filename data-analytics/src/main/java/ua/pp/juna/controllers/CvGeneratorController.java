package ua.pp.juna.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.controllers.request.CandidateRequest;
import ua.pp.juna.services.cv.PdfGenerator;

import java.io.ByteArrayInputStream;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cv")
public class CvGeneratorController {
    private final PdfGenerator pdfGenerator;

    @PostMapping
    public ResponseEntity<ByteArrayResource> generateCV(@RequestBody CandidateRequest candidateRequest) {
        pdfGenerator.saveGeneratedCandidateCV(candidateRequest, "/Users/shkaff02/Juna/data-analytics/src/main/resources/static/cv.pdf");
        ByteArrayInputStream pdfInputStream = pdfGenerator.generateCandidateCV(candidateRequest);

        // Create a ByteArrayResource for the response
        byte[] pdfBytes = pdfInputStream.readAllBytes();
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // Build the response
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=candidate_cv.pdf")
                .body(resource);
    }

}
