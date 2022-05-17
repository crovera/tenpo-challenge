package com.tenpo.challenge.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
class SessionConfig {
    protected static final String ROLES = "roles";
    private static final String SECRET_KEY = "JWT_SECRET";
    protected static final String SECRET = System.getenv(SECRET_KEY);
    protected static final Duration SESSION_DURATION = Duration.ofHours(10);

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
