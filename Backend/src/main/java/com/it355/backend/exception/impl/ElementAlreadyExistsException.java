package com.it355.backend.exception.impl;

import com.it355.backend.exception.ResponseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;


public class ElementAlreadyExistsException extends ResponseException {

    public ElementAlreadyExistsException(String message) {
        super(message, HttpServletResponse.SC_CONFLICT);
    }


}
