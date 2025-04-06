package org.example.service;

import org.example.model.*;
import org.example.repository.CartRepository;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return orderRepository.findByUser(user);
    }

    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) return null;

        Order order = new Order();
        order.setUser(user);
        order.setItems(cart.getItems());
        order.setCreatedAt(LocalDateTime.now());

        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);

        cart.getItems().clear(); // clear cart after order
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    public Order changeOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;

        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
