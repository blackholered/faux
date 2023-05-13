package com.it355.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.exception.ResponseException;
import com.it355.backend.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider userAuthenticationProvider;

    public UsernamePasswordAuthFilter(UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {


        try {
        if ("/auth/login".equals(httpServletRequest.getServletPath())
                && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
            CredentialsDTO credentialsDto = MAPPER.readValue(httpServletRequest.getInputStream(), CredentialsDTO.class);

            SecurityContextHolder.getContext().setAuthentication(
                    userAuthenticationProvider.validateCredentials(credentialsDto));
        }
            } catch (ResponseException ex) {
                SecurityContextHolder.clearContext();
                httpServletResponse.setStatus(ex.getResponse());
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                MAPPER.writeValue(httpServletResponse.getOutputStream(), new ErrorResponse(ex.getMessage()));
                return;
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                MAPPER.writeValue(httpServletResponse.getOutputStream(), new ErrorResponse("A fatal error has occurred while processing the request."));
                return;
            }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
 }

