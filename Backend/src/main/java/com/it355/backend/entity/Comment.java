package com.it355.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "comment_id")
    private Integer id;
    @JoinColumn(name = "video", referencedColumnName = "video_id")
    @ManyToOne
    private Video video;

    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "comment")
    private String comment;
    @Column(name = "datetime")
    private Long datetime;


}