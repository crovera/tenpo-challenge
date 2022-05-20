package com.tenpo.challenge.session;

import java.time.Duration;
import java.util.Optional;

class SessionConfig {
    protected static final String ROLES = "roles";
    private static final String SECRET_KEY = "JWT_SECRET";
    protected static final String SECRET = Optional.ofNullable(System.getenv(SECRET_KEY)).orElse("Default");
    protected static final Duration SESSION_DURATION = Duration.ofHours(10);
}
