package org.example.controller;

import org.example.model.Cart;
import org.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SpringBootTest
@Transactional
public class CartControllerTestTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private CartService cartService;

    private CartController cartController;

    @BeforeMethod
    public void setUp() {
        cartController = new CartController(cartService);
    }

    @Test
    public void testGetCart() {
        Cart cart = cartController.getCart(1L);
        assertNotNull(cart);
        assertEquals((Long) 1L, cart.getUserId());
    }

    @Test
    public void testAddToCart() {
        Cart cart = cartController.addToCart(1L, 100L, 3);
        assertNotNull(cart);
        assertTrue(cart.getItems().stream()
                .anyMatch(i -> i.getProductId().equals(100L) && i.getQuantity() == 3));
    }

    @Test
    public void testRemoveFromCart() {
        cartController.addToCart(1L, 200L, 1);
        Cart updatedCart = cartController.removeFromCart(1L, 200L);
        assertTrue(updatedCart.getItems().stream()
                .noneMatch(i -> i.getProductId().equals(200L)));
    }

    @Test
    public void testClearCart() {
        cartController.addToCart(1L, 300L, 2);
        cartController.clearCart(1L);
        Cart cart = cartController.getCart(1L);
        assertTrue(cart.getItems().isEmpty());
    }
}
