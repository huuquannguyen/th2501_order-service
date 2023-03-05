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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderLineService orderLineService;

    @PostMapping("/addToCart")
    @PreAuthorize(value = "hasRole('user')")
    public ApiResponse<OrderLine> addProductToCart(@Valid @RequestBody AddToCartRequest request,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   Principal principal) throws ApiException {
        request.setUserId(principal.getName());
        return ApiResponse.successWithResult(orderLineService.addToCart(request, token));
    }

    @PostMapping("/place")
    public ApiResponse<OrderEntity> orderProduct(@Valid @RequestBody PlaceOrderRequest request,
                                                 @RequestHeader(value = "Authorization") String token,
                                                 Principal principal) throws ApiException {
        request.setUserId(principal.getName());
        return ApiResponse.successWithResult(orderService.placeOrder(request, token));
    }

    @PutMapping("/cancel/{id}")
    public ApiResponse<OrderEntity> cancelOrder(@PathVariable(name = "id") Long orderId) throws ApiException {
        return ApiResponse.successWithResult(orderService.cancelOrder(orderId));
    }

}
