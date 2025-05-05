package org.example.controller;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void getOrdersByUser_returnsOrderList() {
        Long userId = 1L;
        List<Order> mockOrders = List.of(new Order(), new Order());

        when(orderService.getOrdersByUserId(userId)).thenReturn(mockOrders);

        List<Order> result = orderController.getOrdersByUser(userId);

        assertEquals(mockOrders, result);
        verify(orderService).getOrdersByUserId(userId);
    }

    @Test
    void placeOrder_returnsCreatedOrder() {
        Long userId = 1L;
        Order mockOrder = new Order();

        when(orderService.placeOrder(userId)).thenReturn(mockOrder);

        Order result = orderController.placeOrder(userId);

        assertEquals(mockOrder, result);
        verify(orderService).placeOrder(userId);
    }

    @Test
    void updateStatus_returnsUpdatedOrder() {
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.SHIPPED;
        Order updatedOrder = new Order();
        updatedOrder.setStatus(newStatus);

        when(orderService.changeOrderStatus(orderId, newStatus)).thenReturn(updatedOrder);

        Order result = orderController.updateStatus(orderId, newStatus);

        assertEquals(newStatus, result.getStatus());
        verify(orderService).changeOrderStatus(orderId, newStatus);
    }

    @Test
    void cancelOrder_callsService() {
        Long orderId = 1L;

        orderController.cancelOrder(orderId);

        verify(orderService).cancelOrder(orderId);
    }
}
