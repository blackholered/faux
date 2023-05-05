package com.it355.backend.repository;

import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer>, JpaSpecificationExecutor<Video> {

     List<Video> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
     Optional<Video> findById(Integer id);
     List<Video> findByUser(User user);

     Optional<Video> findByUserIdAndId(Integer userId, Integer id);

}
