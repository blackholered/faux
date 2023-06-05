package com.it355.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "likes")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "like_id")
    private Integer id;
    @JoinColumn(name = "video", referencedColumnName = "video_id")
    @ManyToOne
    private Video video;

    @JoinColumn(name = "user", referencedColumnName = "user_id")
    @ManyToOne
    private User user;
}