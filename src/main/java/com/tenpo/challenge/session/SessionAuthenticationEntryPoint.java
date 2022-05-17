package com.tenpo.challenge.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.shared.APIError;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class SessionAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        APIError error = new APIError("Unauthorized", "User must be logged in to perform this action");
        objectMapper.writeValue(response.getOutputStream(), error);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("custom-entry-point");
        super.afterPropertiesSet();
    }
}
