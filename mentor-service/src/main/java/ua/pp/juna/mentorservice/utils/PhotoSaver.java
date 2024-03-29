package ua.pp.juna.mentorservice.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class PhotoSaver {

    private static String IMAGE_FOLDER = "frontend/src/assets/uploads/";
    public String savePhoto(final MultipartFile file, final String folder) {
        final var fileName = file.getOriginalFilename();
        final var filePath = Paths.get(IMAGE_FOLDER + folder + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            log.error("Failed to upload image! {}", e);
            return "";
        }
    }
}
