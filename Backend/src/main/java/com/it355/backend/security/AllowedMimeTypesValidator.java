package com.it355.backend.security;

import com.it355.backend.security.annotations.AllowedMimeTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class AllowedMimeTypesValidator implements ConstraintValidator<AllowedMimeTypes, MultipartFile> {
    private String[] allowedMimeTypes;

    @Override
    public void initialize(AllowedMimeTypes constraintAnnotation) {
        allowedMimeTypes = constraintAnnotation.value();
    }

    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();


        for (String allowedMimeType : allowedMimeTypes) {
            if (!file.getContentType().equals(allowedMimeType)) {
                return false;
            }
        }
        // Check file extension
        String extension = FilenameUtils.getExtension(filename);
        if (!"mp4".equalsIgnoreCase(extension)) {
            return false;
        }

        // Check file signature
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[12];
            if (is.read(header) != header.length) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

        return true; // valid
    }
}
