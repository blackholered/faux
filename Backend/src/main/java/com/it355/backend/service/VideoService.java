package com.it355.backend.service;

import com.it355.backend.dto.VideoFileDTO;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    List<Video> findAll();
    Page<Video> findPaginated(Pageable pageable);
    List<Video> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);

    Video findById(Integer id);

    Video save(VideoFileDTO videoFileDTO, User user) throws IOException;

    List<Video> findByUser(User user);

    Video findByUserIdAndId(Integer userId, Integer id);

    Video update(Video video);

    void delete(Video video);

}
