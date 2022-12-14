package com.example.order_service.service;

import com.example.order_service.controller.request.AddToCartRequest;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;

import java.util.List;

public interface OrderLineService {

    OrderLine addToCart(AddToCartRequest request) throws ApiException;


}
