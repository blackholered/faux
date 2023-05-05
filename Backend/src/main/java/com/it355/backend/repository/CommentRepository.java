package com.it355.backend.repository;

import com.it355.backend.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    Long countByVideoId(Integer videoId);

    List<Comment> findByVideoIdOrderByDatetimeDesc(Integer videoId, Pageable pageable);

}
