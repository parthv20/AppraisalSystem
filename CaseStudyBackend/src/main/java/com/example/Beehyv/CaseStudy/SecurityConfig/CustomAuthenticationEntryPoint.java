package com.example.Beehyv.CaseStudy.SecurityConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component  // Marks this class as a Spring component, allowing Spring to automatically detect it and manage its lifecycle
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method is invoked whenever an unauthenticated user attempts to access a protected resource
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // Sends an HTTP 401 Unauthorized response when an authentication failure occurs
        // The response will include the exception message explaining the authentication error
        response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
    }
}
