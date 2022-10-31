package com.example.order_service.service;

import com.example.order_service.controller.response.OrderResponse;

public interface OrderService {
    public OrderResponse addToCart(Long productId);
}
