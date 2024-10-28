package com.mariesto.orderservice.controller;

import com.mariesto.orderservice.model.CreateOrderRequest;
import com.mariesto.orderservice.model.OrderDetailResponse;
import com.mariesto.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/orders", produces = "application/json")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = orderService.createOrder(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId)
                .toUri();
        return ResponseEntity.created(location).body(orderId);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<Object> getOrderDetail(@PathVariable String orderId) {
        OrderDetailResponse response = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{orderId}")
    public ResponseEntity<Object> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
