package com.it355.backend.controller;


import com.it355.backend.response.SuccessResponse;
import com.it355.backend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/count")
    public ResponseEntity<Object> getLikesCount(
            @RequestParam("id") int id
    ) {
        System.out.println(id);
        Long count = likeService.countByVideoId(id);
        return ResponseEntity.ok(new SuccessResponse(count.toString()));
    }


}
