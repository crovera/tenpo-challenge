package com.tenpo.challenge.session;

import java.time.Duration;

class SessionConfig {
    protected static final String ROLES = "roles";
    private static final String SECRET_KEY = "JWT_SECRET";
    protected static final String SECRET = System.getenv(SECRET_KEY);
    protected static final Duration SESSION_DURATION = Duration.ofHours(10);
}
