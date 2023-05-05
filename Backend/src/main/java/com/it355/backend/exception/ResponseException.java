package com.it355.backend.exception;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {
    private int response;

    public ResponseException(String message, int response) {
        super(message);
        this.response = response;
    }
}