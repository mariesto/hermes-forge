package com.mariesto.orderservice.service;

import com.mariesto.orderservice.constant.OrderStatus;
import com.mariesto.orderservice.model.CreateOrderRequest;
import com.mariesto.orderservice.model.OrderDetailResponse;
import com.mariesto.orderservice.persistence.entity.Order;
import com.mariesto.orderservice.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public String createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setAmount(generateRandomPrice());
        order.setCustomerId(request.customerId());
        orderRepository.save(order);
        log.info("Order created with order-id : {} for customer id : {}", order.getOrderId(), request.customerId());
        return order.getOrderId();
    }

    private BigDecimal generateRandomPrice() {
        double randomValue = 10_000 + (100_000 - 10_000) * new Random().nextDouble();
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }

    public OrderDetailResponse getOrderDetail(String orderId) {
        final Order order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDetailResponse response = modelMapper.map(order, OrderDetailResponse.class);
        log.info("Order detail: {}", response);
        return response;
    }

    public void cancelOrder(String orderId) {
        final Order order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // TODO: handle state transition
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
