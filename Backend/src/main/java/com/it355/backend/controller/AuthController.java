package com.it355.backend.controller;


import com.it355.backend.config.UserAuthenticationProvider;
import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.User;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.response.SuccessResponse;
import com.it355.backend.service.UserService;
import com.it355.backend.utils.CaptchaChecker;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    private final CaptchaChecker captchaChecker;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/status")
    public ResponseEntity<SuccessResponse> checkSession() {
        return ResponseEntity.ok(new SuccessResponse("true"));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> signIn(@AuthenticationPrincipal LoginDTO user) {
        user.setToken(userAuthenticationProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CredentialsDTO credentialsDTO, BindingResult result) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(credentialsDTO));
    }




}
