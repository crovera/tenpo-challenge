package com.tenpo.challenge.user;

import com.tenpo.challenge.security.EncodingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private final static String USERNAME = "TheGuy";
    private final static String PASSWORD = "Stricken05";
    private final static String ENCODED_PASSWORD = "U3RyaWNrZW4wNQ==";

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void loadUserByUsername_Found() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getPassword()).thenReturn(PASSWORD);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        UserDetails result = userService.loadUserByUsername(USERNAME);

        assertEquals(USERNAME, result.getUsername());
        assertEquals(PASSWORD, result.getPassword());
    }

    @Test
    void loadUserByUsername_NotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(USERNAME));

        assertEquals(String.format("User %s not found", USERNAME), exception.getMessage());
    }

    @Test
    void save_Saved() throws UserExistsException {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getPassword()).thenReturn(PASSWORD);

        User savedUser = mock(User.class);
        when(savedUser.getUsername()).thenReturn(USERNAME);
        when(savedUser.getPassword()).thenReturn(ENCODED_PASSWORD);

        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.save(user);

        assertEquals(USERNAME, result.getUsername());
        assertEquals(ENCODED_PASSWORD, result.getPassword());
    }

    @Test
    void save_AlreadySaved() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getPassword()).thenReturn(PASSWORD);

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        Exception exception = assertThrows(UserExistsException.class, () -> userService.save(user));

        assertEquals(String.format("User %s already exists", USERNAME), exception.getMessage());
    }
}
