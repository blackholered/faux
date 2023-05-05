package com.it355.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer video;
    @Size(min = 15, max = 1024, message = "Comment must be between 15 and 1024 characters.")
    private String comment;
}
