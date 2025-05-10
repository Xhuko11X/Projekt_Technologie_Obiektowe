package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTestEasyMock {

    private UserRepository userRepositoryMock;
    private AuthController authController;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepositoryMock = EasyMock.mock(UserRepository.class);
        authController = new AuthController(userRepositoryMock);
        passwordEncoder = new BCryptPasswordEncoder(); // używamy tego samego, co kontroler
    }

    @Test
    void login_successful() {
        String email = "user@example.com";
        String rawPassword = "password123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);

        EasyMock.expect(userRepositoryMock.findByEmail(email)).andReturn(Optional.of(user));
        EasyMock.replay(userRepositoryMock);

        String result = authController.login(email, rawPassword);

        assertEquals("Login successful!", result);
        EasyMock.verify(userRepositoryMock);
    }

    @Test
    void login_invalidPassword() {
        String email = "user@example.com";
        String correctPassword = "correct";
        String wrongPassword = "wrong";
        String hashedPassword = passwordEncoder.encode(correctPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);

        EasyMock.expect(userRepositoryMock.findByEmail(email)).andReturn(Optional.of(user));
        EasyMock.replay(userRepositoryMock);

        String result = authController.login(email, wrongPassword);

        assertEquals("Invalid credentials", result);
        EasyMock.verify(userRepositoryMock);
    }

    @Test
    void login_userNotFound() {
        String email = "nonexistent@example.com";

        EasyMock.expect(userRepositoryMock.findByEmail(email)).andReturn(Optional.empty());
        EasyMock.replay(userRepositoryMock);

        String result = authController.login(email, "anyPassword");

        assertEquals("Invalid credentials", result);
        EasyMock.verify(userRepositoryMock);
    }

    @Test
    void register_successful() {
        String email = "new@example.com";
        String password = "newpass";
        String name = "New User";

        EasyMock.expect(userRepositoryMock.findByEmail(email)).andReturn(Optional.empty());
        userRepositoryMock.save(EasyMock.anyObject(User.class));
        EasyMock.expectLastCall().once();

        EasyMock.replay(userRepositoryMock);

        String result = authController.register(email, password, name);

        assertEquals("Registration successful!", result);
        EasyMock.verify(userRepositoryMock);
    }

    @Test
    void register_emailExists() {
        String email = "existing@example.com";

        User existingUser = new User();
        existingUser.setEmail(email);

        EasyMock.expect(userRepositoryMock.findByEmail(email)).andReturn(Optional.of(existingUser));
        EasyMock.replay(userRepositoryMock);

        String result = authController.register(email, "any", "Name");

        assertEquals("Email is already in use!", result);
        EasyMock.verify(userRepositoryMock);
    }
}
