package com.example.order_service.service;

import com.example.order_service.controller.request.AddToCartRequest;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;

public interface OrderLineService {

    OrderLine addToCart(AddToCartRequest request, String token) throws ApiException;


}
