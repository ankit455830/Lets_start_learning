package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RequestLoggingFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private final String podName = System.getenv("HOSTNAME"); // Kubernetes pod name

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        LocalDateTime timestamp = LocalDateTime.now();

        // Log only to application logs
        logger.info("[{}] Pod: {} | {} {}", timestamp, podName, method, uri);

        chain.doFilter(request, response); // continue request
    }
}
