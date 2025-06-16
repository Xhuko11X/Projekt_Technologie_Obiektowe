package org.example.controller;

import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SpringBootTest
@Transactional
public class AuthControllerTestTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserRepository userRepository;

    private AuthController authController;

    @BeforeMethod
    public void setUp() {
        authController = new AuthController(userRepository);
    }

    @Test
    public void testRegisterSuccess() {
        String result = authController.register("testng@example.com", "testpass", "TestNG");
        assertEquals(result, "Registration successful!");
        assertTrue(userRepository.findByEmail("testng@example.com").isPresent());
    }

    @Test
    public void testRegisterEmailAlreadyUsed() {
        authController.register("duplicate@example.com", "123", "First");
        String result = authController.register("duplicate@example.com", "456", "Second");
        assertEquals(result, "Email is already in use!");
    }

    @Test
    public void testLoginSuccess() {
        authController.register("user@example.com", "mypassword", "User");
        String result = authController.login("user@example.com", "mypassword");
        assertEquals(result, "Login successful!");
    }

    @Test
    public void testLoginWrongPassword() {
        authController.register("fail@example.com", "correct", "User");
        String result = authController.login("fail@example.com", "wrong");
        assertEquals(result, "Invalid credentials");
    }

    @Test
    public void testLoginUserNotFound() {
        String result = authController.login("ghost@example.com", "pass");
        assertEquals(result, "Invalid credentials");
    }
}
