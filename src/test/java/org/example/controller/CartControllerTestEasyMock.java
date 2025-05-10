package org.example.controller;

import org.example.model.Cart;
import org.example.service.CartService;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTestEasyMock {

    private CartService cartServiceMock;
    private CartController cartController;

    @BeforeEach
    void setUp() {
        cartServiceMock = EasyMock.mock(CartService.class);
        cartController = new CartController(cartServiceMock);
    }

    @Test
    void getCart_returnsCart() {
        Long userId = 1L;
        Cart mockCart = new Cart();

        EasyMock.expect(cartServiceMock.getCartByUserId(userId)).andReturn(mockCart);
        EasyMock.replay(cartServiceMock);

        Cart result = cartController.getCart(userId);

        assertEquals(mockCart, result);
        EasyMock.verify(cartServiceMock);
    }

    @Test
    void addToCart_returnsUpdatedCart() {
        Long userId = 1L;
        Long productId = 2L;
        int quantity = 3;
        Cart updatedCart = new Cart();

        EasyMock.expect(cartServiceMock.addToCart(userId, productId, quantity)).andReturn(updatedCart);
        EasyMock.replay(cartServiceMock);

        Cart result = cartController.addToCart(userId, productId, quantity);

        assertEquals(updatedCart, result);
        EasyMock.verify(cartServiceMock);
    }

    @Test
    void removeFromCart_returnsUpdatedCart() {
        Long userId = 1L;
        Long productId = 2L;
        Cart updatedCart = new Cart();

        EasyMock.expect(cartServiceMock.removeFromCart(userId, productId)).andReturn(updatedCart);
        EasyMock.replay(cartServiceMock);

        Cart result = cartController.removeFromCart(userId, productId);

        assertEquals(updatedCart, result);
        EasyMock.verify(cartServiceMock);
    }

    @Test
    void clearCart_executesWithoutError() {
        Long userId = 1L;

        cartServiceMock.clearCart(userId);
        EasyMock.expectLastCall().once();

        EasyMock.replay(cartServiceMock);

        cartController.clearCart(userId);

        EasyMock.verify(cartServiceMock);
    }
}
