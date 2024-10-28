package com.mariesto.orderservice.persistence.entity;

import com.mariesto.orderservice.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal amount;

    private String customerId;
}
