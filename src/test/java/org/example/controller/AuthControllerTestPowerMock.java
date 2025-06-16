//package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

/*import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuthController.class)
public class AuthControllerTestPowerMock {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthController authController;

    @Before
    public void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        // Mockowanie konstruktora BCryptPasswordEncoder
        PowerMockito.whenNew(BCryptPasswordEncoder.class).withNoArguments().thenReturn(passwordEncoder);

        authController = new AuthController(userRepository);
    }

    @Test
    public void login_successful() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encoded";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        String result = authController.login(email, password);
        assertEquals("Login successful!", result);
    }

    @Test
    public void login_invalidCredentials() {
        String email = "test@example.com";
        String password = "wrong";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        String result = authController.login(email, password);
        assertEquals("Invalid credentials", result);
    }

    @Test
    public void register_successful() {
        String email = "new@example.com";
        String password = "pass";
        String name = "John";
        String encodedPassword = "encoded";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = authController.register(email, password, name);
        assertEquals("Registration successful!", result);

        // Upewniamy się, że metoda save została wywołana
        Mockito.verify(userRepository).save(any(User.class));
    }

    @Test
    public void register_emailAlreadyUsed() {
        String email = "existing@example.com";
        String password = "pass";
        String name = "Anna";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        String result = authController.register(email, password, name);
        assertEquals("Email is already in use!", result);

        // Upewniamy się, że metoda save NIE została wywołana
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }
}
*/