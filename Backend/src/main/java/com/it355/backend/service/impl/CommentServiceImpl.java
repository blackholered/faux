package com.it355.backend.service.impl;

import com.it355.backend.dto.CommentDTO;
import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.Comment;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.repository.CommentRepository;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.service.CommentService;
import com.it355.backend.service.UserService;
import com.it355.backend.service.VideoService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Long countByVideoId(Integer videoId) {
        return commentRepository.countByVideoId(videoId);
    }

    @Override
    public List<Comment> findByVideoIdOrderByDatetimeDesc(Integer videoId, Pageable pageable) {
        return commentRepository.findByVideoIdOrderByDatetimeDesc(videoId, pageable);
    }

    @Override
    public Comment save(CommentDTO commentDTO, Video video, User user) {

        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setVideo(video);
        comment.setDatetime(System.currentTimeMillis() / 1000L);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}
