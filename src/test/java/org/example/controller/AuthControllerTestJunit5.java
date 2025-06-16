package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTestJunit5 {

    @Autowired
    private UserRepository userRepository;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(userRepository);
    }

    @Test
    void testRegisterSuccess() {
        String result = authController.register("junit@example.com", "password", "JUnit");
        assertEquals("Registration successful!", result);
        assertTrue(userRepository.findByEmail("junit@example.com").isPresent());
    }

    @Test
    void testRegisterEmailAlreadyUsed() {
        authController.register("duplicate@example.com", "pass1", "User1");
        String result = authController.register("duplicate@example.com", "pass2", "User2");
        assertEquals("Email is already in use!", result);
    }

    @Test
    void testLoginSuccess() {
        authController.register("login@example.com", "secure", "LoginUser");
        String result = authController.login("login@example.com", "secure");
        assertEquals("Login successful!", result);
    }

    @Test
    void testLoginWrongPassword() {
        authController.register("wrongpass@example.com", "realpass", "User");
        String result = authController.login("wrongpass@example.com", "wrongpass");
        assertEquals("Invalid credentials", result);
    }

    @Test
    void testLoginUserNotFound() {
        String result = authController.login("notfound@example.com", "nopass");
        assertEquals("Invalid credentials", result);
    }
}
