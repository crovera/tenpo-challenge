package com.tenpo.challenge.session;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.shared.APIError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
public class SessionLogoutSuccessHandler implements LogoutSuccessHandler {
    private final SessionCacheService sessionCacheService;
    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        response.setContentType(APPLICATION_JSON_VALUE);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String message;
            String token = authorizationHeader.substring("Bearer ".length());
            String username = getUsernameFromToken(token);
            if (sessionCacheService.isPresent(token)) {
                sessionCacheService.remove(token);
                log.info("User {} successfully logged out", username);
                message = String.format("User %s successfully logged out", username);
            } else {
                log.info("User {} successfully logged out", username);
                message = String.format("User %s was already logged out", username);
            }

            response.setStatus(SC_OK);
            objectMapper.writeValue(response.getOutputStream(), new Logout(message));
        } else {
            response.setStatus(SC_BAD_REQUEST);
            APIError error = new APIError("Bad request", "Missing authorization header");
            objectMapper.writeValue(response.getOutputStream(), error);
        }
    }

    private String getUsernameFromToken(String token) {
        try {
            return JWT.decode(token).getSubject();
        } catch (Exception exception) {
            return "Unknown";
        }
    }
}
