package com.it355.backend.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it355.backend.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ExceptionHandler(ResponseException.class)
    public void handleResponseException(ResponseException ex, HttpServletResponse response) throws IOException {
        response.setStatus(ex.getResponse());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        MAPPER.writeValue(response.getOutputStream(), new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        MAPPER.writeValue(response.getOutputStream(), new ErrorResponse("A fatal error has occurred while processing the request."));
    }


}