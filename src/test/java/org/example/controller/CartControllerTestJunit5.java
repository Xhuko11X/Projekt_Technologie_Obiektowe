package org.example.controller;

import org.example.model.Cart;
import org.example.model.User;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.example.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartControllerTestJunit5 {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private CartController cartController;
    private User testUser;

    @BeforeEach
    void setUp() {
        cartController = new CartController(cartService);

        testUser = new User();
        testUser.setEmail("user1@example.com");
        testUser.setName("JUnit User");
        testUser.setPassword("encoded");
        testUser = userRepository.save(testUser);

        Cart cart = new Cart();
        cart.setUser(testUser);
        cart.setItems(Collections.emptyList());
        cartRepository.save(cart);
    }

    @Test
    void testGetCart() {
        Cart cart = cartController.getCart(testUser.getId());
        assertNotNull(cart);
        assertEquals(testUser.getId(), cart.getUser().getId());
    }

    @Test
    void testAddToCart() {
        Cart cart = cartController.addToCart(testUser.getId(), 100L, 2);
        assertNotNull(cart);
        assertTrue(cart.getItems().stream()
                .anyMatch(i -> i.getProductId().equals(100L) && i.getQuantity() == 2));
    }

    @Test
    void testRemoveFromCart() {
        cartController.addToCart(testUser.getId(), 200L, 1);
        Cart cart = cartController.removeFromCart(testUser.getId(), 200L);
        assertTrue(cart.getItems().stream()
                .noneMatch(i -> i.getProductId().equals(200L)));
    }

    @Test
    void testClearCart() {
        cartController.addToCart(testUser.getId(), 300L, 1);
        cartController.clearCart(testUser.getId());
        Cart cart = cartController.getCart(testUser.getId());
        assertTrue(cart.getItems().isEmpty());
    }
}
