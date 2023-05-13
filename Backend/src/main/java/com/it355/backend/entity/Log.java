package com.it355.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "logs")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include

    @Column(name = "log_id")
    private Integer id;
    @Column(name = "exception")
    private String exception;
    @Column(name = "class")
    private String className;
    @Column(name = "message")
    private String message;
    @Column(name = "stack_trace")
    private String stackTrace;
    @Column(name = "method_name")
    private String methodName;
    @JoinColumn(name = "log_user", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

}