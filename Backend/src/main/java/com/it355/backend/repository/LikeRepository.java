package com.it355.backend.repository;

import com.it355.backend.entity.Comment;
import com.it355.backend.entity.Like;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer>, JpaSpecificationExecutor<Like> {
    Long countByVideoId(Integer videoId);

    Optional<Like> findByVideoAndUser(Video video, User user);


}
