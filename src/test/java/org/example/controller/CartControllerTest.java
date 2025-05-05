package org.example.controller;

import org.example.model.Cart;
import org.example.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {

    private CartService cartService;
    private CartController cartController;

    @BeforeEach
    void setUp() {
        cartService = mock(CartService.class);
        cartController = new CartController(cartService);
    }

    @Test
    void getCart_returnsCart() {
        Long userId = 1L;
        Cart mockCart = new Cart();
        when(cartService.getCartByUserId(userId)).thenReturn(mockCart);

        Cart result = cartController.getCart(userId);

        assertEquals(mockCart, result);
        verify(cartService).getCartByUserId(userId);
    }

    @Test
    void addToCart_returnsUpdatedCart() {
        Long userId = 1L;
        Long productId = 2L;
        int quantity = 3;
        Cart updatedCart = new Cart();

        when(cartService.addToCart(userId, productId, quantity)).thenReturn(updatedCart);

        Cart result = cartController.addToCart(userId, productId, quantity);

        assertEquals(updatedCart, result);
        verify(cartService).addToCart(userId, productId, quantity);
    }

    @Test
    void removeFromCart_returnsUpdatedCart() {
        Long userId = 1L;
        Long productId = 2L;
        Cart updatedCart = new Cart();

        when(cartService.removeFromCart(userId, productId)).thenReturn(updatedCart);

        Cart result = cartController.removeFromCart(userId, productId);

        assertEquals(updatedCart, result);
        verify(cartService).removeFromCart(userId, productId);
    }

    @Test
    void clearCart_invokesServiceMethod() {
        Long userId = 1L;

        cartController.clearCart(userId);

        verify(cartService).clearCart(userId);
    }
}
