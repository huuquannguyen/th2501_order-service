package com.example.order_service.service.impl;

import com.example.order_service.client.ProductClient;
import com.example.order_service.client.response.Product;
import com.example.order_service.client.response.Size;
import com.example.order_service.constant.ErrorCode;
import com.example.order_service.constant.OrderStatus;
import com.example.order_service.controller.request.AddToCartRequest;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.controller.request.SizeRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.exception.ApiException;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    @Override
    public OrderEntity addToCart(AddToCartRequest request) throws ApiException {
        Product product = productClient.getProduct(request.getProductId());
        if (product == null){
            throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        String requestSize = getSizeFromRequest(request.getSizeRequest());
        checkSize(product.getSizes(), requestSize, 1);
        OrderEntity order = new OrderEntity();
        order.setProductId(product.getId());
        order.setPrice(product.getPrice());
        order.setQuantity(1);
        order.setImageUrl(product.getImageUrl());
        order.setStatus(OrderStatus.IN_CART);
        order.setSize(requestSize);
        OrderEntity ordered = orderRepository.save(order);
        log.info("Add product id = {} to cart", ordered.getProductId());
        return ordered;
    }

    @Override
    @Transactional
    public List<OrderEntity> placeOrder(PlaceOrderRequest request) throws ApiException {
        List<OrderEntity> selectedOrder = new ArrayList<>();
        for (Long orderId: request.getOrderIds()) {
            Optional<OrderEntity> optional = orderRepository.findByIdAndStatusEquals(orderId, OrderStatus.IN_CART);
            if(optional.isEmpty()){
                log.error("Order with id {} is not exist", orderId);
                throw new ApiException(ErrorCode.ORDER_NOT_EXIST);
            }
            OrderEntity order = optional.get();
            Product product = productClient.getProduct(order.getProductId());
            if(product == null){
                throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
            }
            checkSize(product.getSizes(), order.getSize(), order.getQuantity());
            order.setStatus(OrderStatus.ORDERED);
            order.setAddress(request.getRecipientInformation().getAddress().toString());
            order.setRecipientName(request.getRecipientInformation().getName());
            order.setAddressType(request.getRecipientInformation().getAddress().getAddressType().getType());
            order.setRecipientPhoneNumber(request.getRecipientInformation().getPhoneNumber());
            selectedOrder.add(order);
        }
        List<OrderEntity> successOrder = orderRepository.saveAll(selectedOrder);
        log.info("Place successfully");
        return successOrder;
    }

    private void checkSize(List<Size> sizes, String sizeRequest, int quantity) throws ApiException {
        Optional<Size> optional = sizes.stream().filter(s -> s.getSize().equals(sizeRequest)).findFirst();
        if(optional.isEmpty()){
            throw new ApiException(ErrorCode.SIZE_NOT_EXIST);
        }
        Size size = optional.get();
        if(size.getQuantity() <= 0){
            throw new ApiException(ErrorCode.PRODUCT_OUT_OF_SIZE);
        }
        if(size.getQuantity() < quantity){
            throw new ApiException(ErrorCode.SIZE_NOT_ENOUGH);
        }
    }

    private String getSizeFromRequest(SizeRequest request){
        return Objects.isNull(request.getSizeCharacter()) ? String.valueOf(request.getSizeNumber()) :
                request.getSizeCharacter().getCode();
    }
}
