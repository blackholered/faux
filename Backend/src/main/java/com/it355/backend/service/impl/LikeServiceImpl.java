package com.it355.backend.service.impl;


import com.it355.backend.entity.Like;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.exception.impl.ElementAlreadyExistsException;
import com.it355.backend.exception.impl.NoElementException;
import com.it355.backend.repository.LikeRepository;
import com.it355.backend.service.LikeService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public Long countByVideoId(Integer videoId) {
        return likeRepository.countByVideoId(videoId);
    }

    @Override
    public Like save(Like like) {
       Optional<Like> liked =  likeRepository.findByVideoAndUser(like.getVideo(), like.getUser());

       if (liked.isPresent()) {
           throw new ElementAlreadyExistsException("You've already liked this video.");
       }
      return likeRepository.save(like);

    }

    @Override
    public Like findByVideoAndUser(Video video, User user) {

    }
}
