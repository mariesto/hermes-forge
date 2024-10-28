package com.mariesto.orderservice.model;

import com.mariesto.orderservice.constant.OrderStatus;

import java.math.BigDecimal;

public record OrderDetailResponse(String orderId, OrderStatus orderStatus, BigDecimal amount, String customerId) {
}
