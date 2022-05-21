package com.tenpo.challenge.session;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.tenpo.challenge.session.SessionConfig.SESSION_DURATION;

@Service
public class SessionCacheService {
    private final RedisTemplate<String, String> activeSessions;

    public SessionCacheService(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();

        activeSessions = redisTemplate;
    }

    public void add(String token, String username) {
        activeSessions.opsForValue().set(token, username, SESSION_DURATION);
    }

    public void remove(String token) {
        activeSessions.delete(token);
    }

    public boolean isPresent(String token) {
        return activeSessions.opsForValue().get(token) != null;
    }
}
