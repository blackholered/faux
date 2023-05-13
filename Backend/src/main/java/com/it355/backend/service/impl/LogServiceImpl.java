package com.it355.backend.service.impl;

import com.it355.backend.entity.Comment;
import com.it355.backend.entity.Log;
import com.it355.backend.repository.CommentRepository;
import com.it355.backend.repository.LogRepository;
import com.it355.backend.service.LogService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;


    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

}
