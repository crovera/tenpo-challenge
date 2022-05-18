package com.tenpo.challenge.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.audit.AuditFilter;
import com.tenpo.challenge.audit.AuditService;
import com.tenpo.challenge.session.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;
    private final SessionCacheService sessionCacheService;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.formLogin().disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.exceptionHandling().authenticationEntryPoint(new SessionAuthenticationEntryPoint(objectMapper));

        http.addFilterBefore(new SessionAuthorizationFilter(sessionCacheService, objectMapper),
                UsernamePasswordAuthenticationFilter.class);

        SessionAuthenticationFilter authenticationFilter =
                new SessionAuthenticationFilter(authenticationManagerBean(), sessionCacheService, objectMapper);
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.addFilter(authenticationFilter);

        http.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new SessionLogoutSuccessHandler(sessionCacheService, objectMapper))
                .permitAll();

        http.addFilterBefore(new AuditFilter(auditService, objectMapper), LogoutFilter.class);

        http.authorizeRequests()
                .antMatchers(POST, "/api/users").permitAll()
                .antMatchers(GET, "/api/calc/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(GET, "/api/audit/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(GET, "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated();
    }
}
