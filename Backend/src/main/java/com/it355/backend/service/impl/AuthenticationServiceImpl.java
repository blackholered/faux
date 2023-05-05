package com.it355.backend.service.impl;

import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.User;
import com.it355.backend.exception.impl.BadRequestException;
import com.it355.backend.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AuthenticationServiceImpl {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public LoginDTO authenticate(CredentialsDTO credentialsDto) {


        User user = userService.findByUsername(credentialsDto.getUsername());

        if (!passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            throw new BadRequestException("Please make sure you have entered the correct password");
        }

        return new LoginDTO(user.getId(), user.getUsername(), "token");

    }

    public LoginDTO findById(String id) {

        User user = userService.findById(Integer.parseInt(id));

        return new LoginDTO(user.getId(), user.getUsername(), "token");
    }
}
