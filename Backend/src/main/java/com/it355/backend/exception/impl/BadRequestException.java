package com.it355.backend.exception.impl;

import com.it355.backend.exception.ResponseException;
import jakarta.servlet.http.HttpServletResponse;

public class BadRequestException extends ResponseException {

    public BadRequestException(String message) {
        super(message, HttpServletResponse.SC_BAD_REQUEST);
    }


}