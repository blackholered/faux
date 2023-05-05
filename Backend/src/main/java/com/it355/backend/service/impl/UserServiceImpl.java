package com.it355.backend.service.impl;

import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.exception.impl.BadRequestException;
import com.it355.backend.exception.impl.ElementAlreadyExistsException;
import com.it355.backend.exception.impl.NoElementException;
import com.it355.backend.repository.UserRepository;
import com.it355.backend.repository.VideoRepository;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.service.UserService;
import com.it355.backend.service.VideoService;
import com.it355.backend.utils.CaptchaChecker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CaptchaChecker captchaChecker;

    private final PasswordEncoder passwordEncoder;



    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoElementException("User is not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoElementException("User is not found"));
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoElementException("User is not found"));
    }

    @Override
    public User save(CredentialsDTO dto) {


        Optional<User> user = userRepository.findByUsername(dto.getUsername());

        if (user.isPresent()) {
            throw new ElementAlreadyExistsException("Sorry, this username already exists.");
        }

        user = userRepository.findByEmail(dto.getEmail());

        if (user.isPresent()) {
            throw new ElementAlreadyExistsException("Sorry, this email already exists.");
        }

        if (!captchaChecker.checkCaptcha(dto.getCaptchaKey())) {
            throw new BadRequestException("Invalid captcha response.");
        }

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(newUser);
    }


}
