package com.it355.backend.service;

import com.it355.backend.dto.VideoFileDTO;
import com.it355.backend.entity.Like;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;

public interface LikeService {

    Long countByVideoId(Integer videoId);

    Like save(Like like);

    Like findByVideoAndUser(Video video, User user);

    void delete(Like like);


}
