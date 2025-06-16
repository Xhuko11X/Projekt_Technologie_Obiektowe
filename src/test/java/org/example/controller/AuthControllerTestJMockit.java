/*package org.example.controller;

import mockit.*;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mockit.integration.junit4.JMockit;
import org.junit.runner.RunWith;


import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(mockit.integration.junit4.JMockit.class)
public class AuthControllerTestJMockit {

    @Mocked
    UserRepository userRepository;

    @Mocked
    BCryptPasswordEncoder passwordEncoder;

    @Tested
    AuthController authController;

    @Test
    public void testLogin_Success() {
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
    public void testLogin_InvalidCredentials() {
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
    public void testRegister_Success() {
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

        String result = authController.register(email, password, name);
        assertEquals("Registration successful!", result);

        new Verifications() {{
            userRepository.save((User) any);
            times = 1;
        }};
    }

    @Test
    public void testRegister_EmailInUse() {
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
*/