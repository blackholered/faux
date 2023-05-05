package com.it355.backend.exception.impl;

import com.it355.backend.exception.ResponseException;
import jakarta.servlet.http.HttpServletResponse;

public class NoElementException extends ResponseException {

    public NoElementException(String message) {
        super(message, HttpServletResponse.SC_NOT_FOUND);
    }


}