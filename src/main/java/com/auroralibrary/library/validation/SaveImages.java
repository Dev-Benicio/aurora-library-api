package com.auroralibrary.library.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;


@Component
public class SaveImages {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    public String saveImage(MultipartFile image, String folder) {
        try {
            String directory = Path.of(uploadDirectory, folder).toString();

            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path targetLocation = Path.of(directory, fileName);

            Files.createDirectories(targetLocation.getParent());

            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível salvar o arquivo", ex);
        }
    }
}
