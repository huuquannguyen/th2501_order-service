package com.example.order_service.controller;

import com.example.order_service.controller.request.AddToCartRequest;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;
import com.example.order_service.model.ApiResponse;
import com.example.order_service.service.OrderLineService;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderLineService orderLineService;

    @PostMapping("/addToCart")
    public ApiResponse<OrderLine> addProductToCart(@Valid @RequestBody AddToCartRequest request) throws ApiException {
        return ApiResponse.successWithResult(orderLineService.addToCart(request));
    }

    @PostMapping("/place")
    public ApiResponse<OrderEntity> orderProduct(@Valid @RequestBody PlaceOrderRequest request) throws ApiException {
        return ApiResponse.successWithResult(orderService.placeOrder(request));
    }

}
