package com.it355.backend.controller;


import com.it355.backend.dto.LoginDTO;
import com.it355.backend.dto.VideoDTO;
import com.it355.backend.dto.VideoFileDTO;
import com.it355.backend.entity.Like;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.response.SuccessResponse;
import com.it355.backend.service.LikeService;
import com.it355.backend.service.UserService;
import com.it355.backend.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    private final UserService userService;

    private final VideoService videoService;

    @GetMapping("/count")
    public ResponseEntity<Object> getLikesCount(
            @RequestParam("id") int id
    ) {
        Long count = likeService.countByVideoId(id);
        return ResponseEntity.ok(new SuccessResponse(count.toString()));
    }

    @GetMapping("/hasUserLiked")
    public ResponseEntity<Object> getLiked(
            @RequestParam("id") int id,
            @AuthenticationPrincipal LoginDTO dto) {


        User user = userService.findById(dto.getId());
        Video video = videoService.findById(id);
        likeService.findByVideoAndUser(video, user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(true);
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addLike(
            @Valid @RequestBody VideoDTO videoDTO,
            @AuthenticationPrincipal LoginDTO dto,
            BindingResult result) {



        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }

            User user = userService.findById(dto.getId());
            Video video = videoService.findById(videoDTO.getId());

            Like like = new Like();
            like.setVideo(video);
            like.setUser(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(likeService.save(like));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteLike(
            @RequestParam("id") Integer id,
            @AuthenticationPrincipal LoginDTO dto
    ) {

        User user = userService.findById(dto.getId());

        Video video = videoService.findById(id);

        Like like = likeService.findByVideoAndUser(video, user);

        likeService.delete(like);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
