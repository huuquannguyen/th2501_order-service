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
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;
import com.example.order_service.repository.OrderLineRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderLineService;
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
public class OrderLineServiceImpl implements OrderLineService {

    private final ProductClient productClient;

    private final OrderLineRepository orderLineRepository;

    @Override
    public OrderLine addToCart(AddToCartRequest request) throws ApiException {
        Product product = productClient.getProduct(request.getProductId());
        if (product == null){
            throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        String requestSize = getSizeFromRequest(request.getSizeRequest());
        checkSize(product.getSizes(), requestSize, request.getSizeRequest().getColor().getColor(), 1);
        OrderLine order = new OrderLine();
        order.setProductId(product.getId());
        order.setPrice(product.getPrice());
        order.setColor(request.getSizeRequest().getColor().getColor());
        order.setQuantity(1);
        order.setImageUrl(product.getImageUrl());
        order.setStatus(OrderStatus.IN_CART);
        order.setSize(requestSize);
        OrderLine ordered = orderLineRepository.save(order);
        log.info("Add product id = {} to cart", ordered.getProductId());
        return ordered;
    }

    public static void checkSize(List<Size> sizes, String sizeRequest, String color, int quantity) throws ApiException {
        Optional<Size> optional = sizes.stream()
                                        .filter(s -> s.getSize().equals(sizeRequest) && s.getColor().equals(color))
                                        .findFirst();
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

    public static String getSizeFromRequest(SizeRequest request){
        return Objects.isNull(request.getSizeCharacter()) ? String.valueOf(request.getSizeNumber()) :
                request.getSizeCharacter().getCode();
    }
}
