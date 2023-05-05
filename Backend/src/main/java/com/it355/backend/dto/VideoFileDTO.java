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
public class VideoFileDTO extends VideoDTO {
    @AllowedMimeTypes("video/mp4")
    @MaxFileSize(value = 1024 * 1024 * 1024, message = "File is too big. It can only be 1024 MB.")
    private MultipartFile file;


}
