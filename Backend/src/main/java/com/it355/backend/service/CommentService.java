package com.it355.backend.service;

import com.it355.backend.dto.CommentDTO;
import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.Comment;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Long countByVideoId(Integer videoId);

    List<Comment> findByVideoIdOrderByDatetimeDesc(Integer videoId, Pageable pageable);

    Comment save(CommentDTO commentDTO, Video video, User user);
}
