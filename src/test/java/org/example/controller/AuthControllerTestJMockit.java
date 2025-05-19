package org.example.controller;

import mockit.*;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTestJMockit {

    @Mocked
    UserRepository userRepository;

    @Mocked
    BCryptPasswordEncoder passwordEncoder;

    @Tested
    AuthController authController;

    @Test
    void testLogin_Success() {
        String email = "user@example.com";
        String password = "password";
        String encodedPassword = "$2a$10$hashed";

        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword(encodedPassword);

        new Expectations() {{
            userRepository.findByEmail(email);
            result = Optional.of(mockUser);

            passwordEncoder.matches(password, encodedPassword);
            result = true;
        }};

        String result = authController.login(email, password);
        assertEquals("Login successful!", result);
    }

    @Test
    void testLogin_InvalidCredentials() {
        String email = "user@example.com";
        String password = "wrongpassword";

        new Expectations() {{
            userRepository.findByEmail(email);
            result = Optional.empty();
        }};

        String result = authController.login(email, password);
        assertEquals("Invalid credentials", result);
    }

    @Test
    void testRegister_Success() {
        String email = "new@example.com";
        String name = "John";
        String password = "password";
        String encodedPassword = "$2a$10$encoded";

        new Expectations() {{
            userRepository.findByEmail(email);
            result = Optional.empty();

            passwordEncoder.encode(password);
            result = encodedPassword;
        }};

        new Verifications() {{
            userRepository.save((User) any);
            times = 1;
        }};

        String result = authController.register(email, password, name);
        assertEquals("Registration successful!", result);
    }

    @Test
    void testRegister_EmailInUse() {
        String email = "used@example.com";
        String password = "pass";
        String name = "Anna";

        new Expectations() {{
            userRepository.findByEmail(email);
            result = Optional.of(new User());
        }};

        String result = authController.register(email, password, name);
        assertEquals("Email is already in use!", result);
    }
}
