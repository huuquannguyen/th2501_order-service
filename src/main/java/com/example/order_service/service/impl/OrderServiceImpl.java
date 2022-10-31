package com.example.order_service.service.impl;

import com.example.order_service.controller.response.OrderResponse;
import com.example.order_service.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse addToCart(Long productId) {
        return null;
    }
}
