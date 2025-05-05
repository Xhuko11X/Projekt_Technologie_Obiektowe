package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private UserRepository userRepository;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authController = new AuthController(userRepository);
    }

    @Test
    void login_successful() {
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String result = authController.login(email, rawPassword);

        assertEquals("Login successful!", result);
    }

    @Test
    void login_invalidPassword() {
        String email = "test@example.com";
        String rawPassword = "wrongPassword";
        String correctEncodedPassword = new BCryptPasswordEncoder().encode("password123");

        User user = new User();
        user.setEmail(email);
        user.setPassword(correctEncodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String result = authController.login(email, rawPassword);

        assertEquals("Invalid credentials", result);
    }

    @Test
    void login_userNotFound() {
        String email = "notfound@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        String result = authController.login(email, "anyPassword");

        assertEquals("Invalid credentials", result);
    }

    @Test
    void register_successful() {
        String email = "new@example.com";
        String password = "password123";
        String name = "New User";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        String result = authController.register(email, password, name);

        assertEquals("Registration successful!", result);

        // Capture the user saved
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals(email, savedUser.getEmail());
        assertEquals(name, savedUser.getName());
        assertTrue(new BCryptPasswordEncoder().matches(password, savedUser.getPassword()));
    }

    @Test
    void register_emailAlreadyInUse() {
        String email = "existing@example.com";

        User existingUser = new User();
        existingUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        String result = authController.register(email, "password123", "Existing User");

        assertEquals("Email is already in use!", result);
        verify(userRepository, never()).save(any(User.class));
    }
}
