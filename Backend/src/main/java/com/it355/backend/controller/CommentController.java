package com.it355.backend.controller;


import com.it355.backend.dto.CommentDTO;
import com.it355.backend.dto.LoginDTO;
import com.it355.backend.entity.Comment;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.response.SuccessResponse;
import com.it355.backend.service.CommentService;
import com.it355.backend.service.UserService;
import com.it355.backend.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final VideoService videoService;
    private final CommentService commentService;

    private final UserService userService;



    @PostMapping("/add")
    public ResponseEntity<Object> myEndpoint(
            @AuthenticationPrincipal LoginDTO dto,
            @Valid @RequestBody CommentDTO commentDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }

        Video video = videoService.findById(commentDTO.getVideo());
        User user = userService.findById(dto.getId());

        return ResponseEntity.ok(commentService.save(commentDTO, video, user));
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getComments(
            @RequestParam("id") Integer id,
            @RequestParam(defaultValue = "1") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        List<Comment> comments = commentService.findByVideoIdOrderByDatetimeDesc(id, PageRequest.of(offset, limit));
        if (comments.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("There are no comments."));
        }

        return ResponseEntity.ok(comments);


    }


    @GetMapping("/count")
    public ResponseEntity<Object> getCommentCount(
            @RequestParam("id") int id
    ) {
        Long count = commentService.countByVideoId(id);


        return ResponseEntity.ok(new SuccessResponse(count.toString()));
    }

}
