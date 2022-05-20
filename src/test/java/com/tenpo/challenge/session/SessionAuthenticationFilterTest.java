package com.tenpo.challenge.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.shared.APIError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class SessionAuthenticationFilterTest {
    private SessionAuthenticationFilter sessionAuthenticationFilter;
    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;

    private final static String USERNAME = "TheGuy";
    private final static String PASSWORD = "Stricken05";

    @BeforeEach
    void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        SessionCacheService sessionCacheService = mock(SessionCacheService.class);
        objectMapper = mock(ObjectMapper.class);
        sessionAuthenticationFilter = new SessionAuthenticationFilter(authenticationManager, sessionCacheService, objectMapper);
    }

    @Test
    void attemptAuthentication_success() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletInputStream inputStream = mock(ServletInputStream.class);
        when(request.getInputStream()).thenReturn(inputStream);

        Credentials credentials = new Credentials(USERNAME, PASSWORD);
        when(objectMapper.readValue(any(ServletInputStream.class), eq(Credentials.class))).thenReturn(credentials);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(token)).thenReturn(authMock);

        Authentication authentication = sessionAuthenticationFilter.attemptAuthentication(request, response);

        verify(authenticationManager).authenticate(token);
        assertEquals(authMock, authentication);
    }

    @Test
    void attemptAuthentication_fail() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletInputStream inputStream = mock(ServletInputStream.class);
        when(request.getInputStream()).thenReturn(inputStream);

        when(objectMapper.readValue(any(ServletInputStream.class), eq(Credentials.class)))
                .thenThrow(new IOException("Error"));

        Exception exception = assertThrows(SessionAuthenticationException.class,
                () -> sessionAuthenticationFilter.attemptAuthentication(request, response));

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void successfulAuthentication() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        UserDetails user = mock(User.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(user);

        sessionAuthenticationFilter.successfulAuthentication(request, response, filterChain, authentication);

        verify(response).setStatus(SC_OK);
        verify(response).setContentType(APPLICATION_JSON_VALUE);
    }

    @Test
    void unsuccessfulAuthentication() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        AuthenticationException exception = new SessionAuthenticationException("Error");

        sessionAuthenticationFilter.unsuccessfulAuthentication(request, response, exception);

        verify(response).setStatus(SC_UNAUTHORIZED);
        verify(response).setContentType(APPLICATION_JSON_VALUE);
        APIError error = new APIError("Unauthorized", "Error");
        verify(objectMapper).writeValue(any(ServletOutputStream.class), eq(error));
    }
}
