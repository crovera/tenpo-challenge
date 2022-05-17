package com.tenpo.challenge.session;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Service;

import static com.tenpo.challenge.session.SessionConfig.SESSION_DURATION;

@Service
public class SessionCacheService {
    private final Cache<String, String> sessions;

    public SessionCacheService(CacheManager cacheManager) {
        CacheConfiguration<String, String> cacheConf = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(300000))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(SESSION_DURATION))
                .build();

        sessions = cacheManager.createCache("sessions", cacheConf);
    }

    public void add(String token, String username) {
        sessions.put(token, username);
    }

    public void remove(String token) {
        sessions.remove(token);
    }

    public boolean isPresent(String token) {
        return sessions.containsKey(token);
    }
}
