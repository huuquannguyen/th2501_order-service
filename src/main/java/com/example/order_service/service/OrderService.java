package com.example.order_service.service;

import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.exception.ApiException;

public interface OrderService {

    OrderEntity placeOrder(PlaceOrderRequest request, String token) throws ApiException;

    OrderEntity cancelOrder(Long orderId) throws ApiException;

}
