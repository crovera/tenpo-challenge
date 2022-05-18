package com.tenpo.challenge.audit;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class AuditFilter extends OncePerRequestFilter {
    private final AuditService auditService;
    private ObjectMapper objectMapper;

    private final static String UNKNOWN = "Unknown";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        filterChain.doFilter(wrappedRequest, response);

        String username;
        if (Arrays.asList("/api/users", "/api/login").contains(request.getRequestURI())) {
            username = getUsernameFromJson(wrappedRequest);
        } else {
            username = getUsernameFromHeader(wrappedRequest);
        }

        Entry entry = new Entry();
        entry.setUsername(username);
        entry.setOperation(request.getRequestURI());
        entry.setMethod(request.getMethod());
        entry.setStatus(response.getStatus());
        entry.setTimeStamp(Instant.now().toString());
        auditService.save(entry);
    }

    private String getUsernameFromJson(ContentCachingRequestWrapper request) throws IOException {
        String username = objectMapper.readValue(request.getContentAsByteArray(), Username.class).username();
        if (username != null) {
            return username;
        }

        return UNKNOWN;
    }

    private String getUsernameFromHeader(ContentCachingRequestWrapper request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring("Bearer ".length());
            try {
                return JWT.decode(token).getSubject();
            } catch (Exception exception) {
                return UNKNOWN;
            }
        }

        return UNKNOWN;
    }
}

