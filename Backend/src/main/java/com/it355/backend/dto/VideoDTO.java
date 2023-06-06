package com.it355.backend.dto;

import com.it355.backend.security.annotations.AllowedMimeTypes;
import com.it355.backend.security.annotations.MaxFileSize;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private int id;
    @Size(min = 5, max = 100, message = "Video name can be between 5 and 100 characters.")
    private String name;
    @Size(max = 256, message = "Video description can be up to 256 characters long.")
    private String description;


}
