package com.it355.backend.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "videos")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "video_id")
    private Integer id;
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    @ManyToOne
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "file")
    private String file;


}
