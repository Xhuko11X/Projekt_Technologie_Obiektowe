package org.example.controller;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.service.OrderService;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTestEasyMock {

    private OrderService orderServiceMock;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderServiceMock = EasyMock.mock(OrderService.class);
        orderController = new OrderController(orderServiceMock);
    }

    @Test
    void getOrdersByUser_returnsOrderList() {
        Long userId = 1L;
        List<Order> mockOrders = List.of(new Order(), new Order());

        EasyMock.expect(orderServiceMock.getOrdersByUserId(userId)).andReturn(mockOrders);
        EasyMock.replay(orderServiceMock);

        List<Order> result = orderController.getOrdersByUser(userId);

        assertEquals(mockOrders, result);
        EasyMock.verify(orderServiceMock);
    }

    @Test
    void placeOrder_returnsNewOrder() {
        Long userId = 1L;
        Order newOrder = new Order();

        EasyMock.expect(orderServiceMock.placeOrder(userId)).andReturn(newOrder);
        EasyMock.replay(orderServiceMock);

        Order result = orderController.placeOrder(userId);

        assertEquals(newOrder, result);
        EasyMock.verify(orderServiceMock);
    }

    @Test
    void updateStatus_returnsUpdatedOrder() {
        Long orderId = 10L;
        OrderStatus newStatus = OrderStatus.SHIPPED;
        Order updatedOrder = new Order();
        updatedOrder.setStatus(newStatus);

        EasyMock.expect(orderServiceMock.changeOrderStatus(orderId, newStatus)).andReturn(updatedOrder);
        EasyMock.replay(orderServiceMock);

        Order result = orderController.updateStatus(orderId, newStatus);

        assertEquals(newStatus, result.getStatus());
        EasyMock.verify(orderServiceMock);
    }

    @Test
    void cancelOrder_executesWithoutError() {
        Long orderId = 5L;

        orderServiceMock.cancelOrder(orderId);
        EasyMock.expectLastCall().once();

        EasyMock.replay(orderServiceMock);

        orderController.cancelOrder(orderId);

        EasyMock.verify(orderServiceMock);
    }
}
