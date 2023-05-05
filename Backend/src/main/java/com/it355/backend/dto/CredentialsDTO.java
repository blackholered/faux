package com.it355.backend.dto;

import com.it355.backend.security.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {
    @NotBlank(message = "Username is required.")
    @Size(min = 1, max = 15, message = "Username must be between 1 and 15 characters.")
    private String username;
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is not valid.")
    private String email;
    @ValidPassword
    private String password;
    private String captchaKey;
}
