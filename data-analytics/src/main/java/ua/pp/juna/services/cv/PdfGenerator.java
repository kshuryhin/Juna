package ua.pp.juna.services.cv;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Text;
import com.itextpdf.kernel.colors.DeviceRgb;
import org.springframework.stereotype.Service;
import ua.pp.juna.controllers.request.CandidateRequest;

import java.io.*;
import java.net.MalformedURLException;

@Service
public class PdfGenerator {

    public ByteArrayInputStream generateCandidateCV(CandidateRequest candidate) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Color for titles
        DeviceRgb titleColor = new DeviceRgb(22, 143, 240);

        // Add candidate photo
        try {
            Image photo = new Image(ImageDataFactory.create("/Users/shkaff02/Juna/frontend/src/assets/uploads/candidates/" +
                    candidate.getPhotoLink()))
                    .setMaxWidth(100)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setBorderRadius(new BorderRadius(50));
            document.add(photo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Add candidate name
        String fullName = candidate.getFirstName() + " " + candidate.getLastName();
        Paragraph name = new Paragraph(fullName)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(name);

        // Add position
        Paragraph position = new Paragraph()
                .add(new Text("Position: ").setFontColor(titleColor))
                .add(new Text(candidate.getPosition()))
                .setFontSize(12);
        document.add(position);

        // Grade
        Paragraph grade = new Paragraph()
                .add(new Text("Grade: ").setFontColor(titleColor))
                .add(new Text(candidate.getGrade()))
                .setFontSize(12);
        document.add(grade);

        // Salary Expectations
        Paragraph salaryExpectations = new Paragraph()
                .add(new Text("Salary Expectations: ").setFontColor(titleColor))
                .add(new Text(Integer.toString(candidate.getSalaryExpectations())))
                .setFontSize(12);
        document.add(salaryExpectations);

        // Add other fields
        // Category
        Paragraph category = new Paragraph()
                .add(new Text("Category: ").setFontColor(titleColor))
                .add(new Text(candidate.getCategory()))
                .setFontSize(12);
        document.add(category);

        // Skills
        Paragraph skills = new Paragraph()
                .add(new Text("Skills: ").setFontColor(titleColor))
                .add(new Text(String.join(", ", candidate.getSkills())))
                .setFontSize(12);
        document.add(skills);

        // Work Experience
        Paragraph workExperience = new Paragraph()
                .add(new Text("Work Experience: ").setFontColor(titleColor))
                .add(new Text(candidate.getWorkExperience()))
                .setFontSize(12);
        document.add(workExperience);

        // Pet Projects Description
        Paragraph petProjectsDescription = new Paragraph()
                .add(new Text("Pet Projects Description: ").setFontColor(titleColor))
                .add(new Text(candidate.getPetProjectsDescription()))
                .setFontSize(12);
        document.add(petProjectsDescription);

        // English Level
        Paragraph englishLevel = new Paragraph()
                .add(new Text("English Level: ").setFontColor(titleColor))
                .add(new Text(candidate.getEnglishLevel()))
                .setFontSize(12);
        document.add(englishLevel);

        // Employment Type
        Paragraph employmentType = new Paragraph()
                .add(new Text("Employment Type: ").setFontColor(titleColor))
                .add(new Text(candidate.getEmploymentType()))
                .setFontSize(12);
        document.add(employmentType);

        // Country and City
        Paragraph location = new Paragraph()
                .add(new Text("Location: ").setFontColor(titleColor))
                .add(new Text(candidate.getCountry() + ", " + candidate.getCity()))
                .setFontSize(12);
        document.add(location);

        // Phone Number
        Paragraph phoneNumber = new Paragraph()
                .add(new Text("Phone Number: ").setFontColor(titleColor))
                .add(new Text(candidate.getPhoneNumber()))
                .setFontSize(12);
        document.add(phoneNumber);

        // Email
        Paragraph email = new Paragraph()
                .add(new Text("Email: ").setFontColor(titleColor))
                .add(new Text(candidate.getEmail()))
                .setFontSize(12);
        document.add(email);

        // LinkedIn Link
        Paragraph linkedinLink = new Paragraph()
                .add(new Text("LinkedIn: ").setFontColor(titleColor))
                .add(new Text(candidate.getLinkedinLink()))
                .setFontSize(12);
        document.add(linkedinLink);

        //Telegram Link
        Paragraph telegramLink = new Paragraph()
                .add(new Text("Telegram: ").setFontColor(titleColor))
                .add(new Text(candidate.getTelegramLink()))
                .setFontSize(12);
        document.add(telegramLink);

        // GitHub Link
        Paragraph githubLink = new Paragraph()
                .add(new Text("GitHub: ").setFontColor(titleColor))
                .add(new Text(candidate.getGithubLink()))
                .setFontSize(12);
        document.add(githubLink);


        // Close the document
        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }


    public void saveGeneratedCandidateCV(CandidateRequest candidate, String filePath) {
        ByteArrayInputStream inputStream = generateCandidateCV(candidate);

        try {
            File outputFile = new File(filePath);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

