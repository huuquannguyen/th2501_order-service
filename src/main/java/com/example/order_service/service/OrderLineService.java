package com.example.order_service.service;

import com.example.order_service.controller.request.AddToCartRequest;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderLineService {

    OrderLine addToCart(AddToCartRequest request, String token) throws ApiException;


}
