package com.example.order_service.service.impl;

import com.example.order_service.client.ProductClient;
import com.example.order_service.client.response.Product;
import com.example.order_service.constant.ErrorCode;
import com.example.order_service.constant.OrderStatus;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.exception.ApiException;
import com.example.order_service.repository.OrderLineRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderLineRepository orderLineRepository;

    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderEntity placeOrder(PlaceOrderRequest request) throws ApiException {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(request.getRecipientInformation().getAddress().toString());
        orderEntity.setRecipientName(request.getRecipientInformation().getName());
        orderEntity.setAddressType(request.getRecipientInformation().getAddress().getAddressType().getType());
        orderEntity.setRecipientPhoneNumber(request.getRecipientInformation().getPhoneNumber());

        List<OrderLine> selectedOrder = new ArrayList<>();
        for (Long orderId: request.getOrderIds()) {
            Optional<OrderLine> optional = orderLineRepository.findByIdAndStatusEquals(orderId, OrderStatus.IN_CART);
            if(optional.isEmpty()){
                log.error("Order with id {} is not exist", orderId);
                throw new ApiException(ErrorCode.ORDER_NOT_EXIST);
            }
            OrderLine order = optional.get();
            Product product = productClient.getProduct(order.getProductId());
            if(product == null){
                throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
            }
            OrderLineServiceImpl.checkSize(product.getSizes(), order.getSize(), order.getColor(), order.getQuantity());
            order.setStatus(OrderStatus.ORDERED);
            order.setOrder(orderEntity);
            selectedOrder.add(order);
        }

        orderEntity.setOrderLines(selectedOrder);
        OrderEntity successOrder = orderRepository.save(orderEntity);
        log.info("Place order id = {} successfully", successOrder.getOrderLines().stream().map(OrderLine::getId));
        return successOrder;
    }
}
