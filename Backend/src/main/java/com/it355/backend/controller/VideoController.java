package com.it355.backend.controller;


import com.it355.backend.dto.LoginDTO;
import com.it355.backend.dto.VideoDTO;
import com.it355.backend.dto.VideoFileDTO;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.response.ErrorResponse;
import com.it355.backend.service.UserService;
import com.it355.backend.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // add the frontend domain here
public class VideoController {
    private final VideoService videoService;
    private final UserService userService;




    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteVideo(
            @RequestParam("id") Integer id,
            @AuthenticationPrincipal LoginDTO dto
    ) {
        Video video = videoService.findByUserIdAndId(dto.getId(), id);
        videoService.delete(video);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/update")
    public ResponseEntity<Object> updateVideo(
            @Valid @RequestBody VideoDTO videoDTO,
            @AuthenticationPrincipal LoginDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }

        Video video = videoService.findByUserIdAndId(dto.getId(), videoDTO.getId());

        video.setName(videoDTO.getName());
        video.setDescription(videoDTO.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(videoService.update(video));
    }




    @PostMapping("/add")
    public ResponseEntity<Object> addVideo(
            @Valid @ModelAttribute VideoFileDTO videoFileDTO,
            @AuthenticationPrincipal LoginDTO dto,
            BindingResult result) {


        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }

        try {

            User user = userService.findById(dto.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(videoService.save(videoFileDTO, user));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to upload video."));
        }
    }


    @PostMapping("/manage")
    public ResponseEntity<Video> manageVideo(
            @AuthenticationPrincipal LoginDTO dto,
            @RequestBody VideoFileDTO videoFileDTO
    ) {

        Video video = videoService.findByUserIdAndId(dto.getId(), videoFileDTO.getId());
        return ResponseEntity.ok(video);

    }


    @PostMapping("/uploader")
    public ResponseEntity<List<Video>> getVideosByToken(
            @AuthenticationPrincipal LoginDTO dto
    ) {
        User user = userService.findById(dto.getId());
        return ResponseEntity.ok(videoService.findByUser(user));

    }


    @GetMapping
    public ResponseEntity<List<Video>> getPaginatedVideos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Video> videosPage = videoService.findPaginated(pageable);

        return ResponseEntity.ok(videosPage.getContent());
    }

    @GetMapping("/get")
    public ResponseEntity<Video> getVideo(
            @RequestParam("id") int id
    ) {
        return ResponseEntity.ok(videoService.findById(id));
    }


    @GetMapping("/search")
    public List<Video> getVideosBySearch(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam("query") String query) {

        return videoService.findByNameContainingOrDescriptionContaining(
                query, query, PageRequest.of(page - 1, limit));
    }


    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> getStatic(@PathVariable String fileName) {
        Path file = Paths.get("C:/Users/korisnik/Desktop/uploads/" + fileName);
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(file));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
