package com.it355.backend.service;

import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.entity.User;

import java.util.Optional;

public interface UserService {

    User findByEmail(String email);
    User findByUsername(String username);
    User findById(Integer id);

    User save(CredentialsDTO dto);

}
