package com.example.order_service.service.impl;

import com.example.order_service.client.ProductClient;
import com.example.order_service.client.response.Product;
import com.example.order_service.constant.ErrorCode;
import com.example.order_service.constant.OrderStatus;
import com.example.order_service.controller.request.PlaceOrderRequest;
import com.example.order_service.entity.OrderEntity;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.event.OrderProductEvent;
import com.example.order_service.exception.ApiException;
import com.example.order_service.model.ApiResponse;
import com.example.order_service.repository.OrderLineRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderLineRepository orderLineRepository;

    private final ProductClient productClient;

    private final ObjectMapper mapper;

    private final StreamBridge streamBridge;

    private final String SERVICE_NAME = "order-service";

    @Override
    @Transactional
    public OrderEntity placeOrder(PlaceOrderRequest request, String token) throws ApiException {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(request.getUserId());
        orderEntity.setAddress(request.getRecipientInformation().getAddress().toString());
        orderEntity.setRecipientName(request.getRecipientInformation().getName());
        orderEntity.setAddressType(request.getRecipientInformation().getAddress().getAddressType().getType());
        orderEntity.setRecipientPhoneNumber(request.getRecipientInformation().getPhoneNumber());

        List<OrderLine> selectedOrderLines = new ArrayList<>();
        for (Long orderLineId : request.getOrderLineIds()) {
            Optional<OrderLine> optional = orderLineRepository.findByIdAndInCartEquals(orderLineId, true);
            if (optional.isEmpty()) {
                log.error("Order with id {} is not exist", orderLineId);
                throw new ApiException(ErrorCode.ORDER_NOT_EXIST);
            }
            OrderLine orderLine = optional.get();
            ApiResponse<Product> response = productClient.getProduct(orderLine.getProductId(), token);
            if (response.getResult() == null) {
                throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
            }
            Product product = mapper.convertValue(response.getResult(), Product.class);
            OrderLineServiceImpl.checkSize(product.getSizes(), orderLine.getSize(), orderLine.getColor(), orderLine.getQuantity());
            orderLine.setInCart(false);
            orderLine.setOrder(orderEntity);
            selectedOrderLines.add(orderLine);
        }

        orderEntity.setPaid(checkPaid());
        orderEntity.setOrderLines(selectedOrderLines);
        orderEntity.setStatus(OrderStatus.ORDERED);
        OrderEntity successOrder = orderRepository.save(orderEntity);
        OrderProductEvent orderProductEvent = new OrderProductEvent(SERVICE_NAME, OrderProductEvent.PLACE_ORDER_EVENT, successOrder.getOrderLines());
        streamBridge.send("orderEvent-out-0", orderProductEvent);
        log.info("Place order id = {} successfully", successOrder.getOrderLines().stream().map(OrderLine::getId).collect(Collectors.toList()));
        return successOrder;
    }

    @Override
    public OrderEntity cancelOrder(Long orderId) throws ApiException {
        Optional<OrderEntity> optional = orderRepository.findById(orderId);
        if (optional.isEmpty()) {
            throw new ApiException(ErrorCode.ORDER_NOT_EXIST);
        }
        OrderEntity order = optional.get();
        if (!order.getStatus().equals(OrderStatus.ORDERED)) {
            throw new ApiException(ErrorCode.CANCEL_ORDER_ERROR);
        }
        order.setStatus(OrderStatus.CANCELED);
        var canceledOrder = orderRepository.save(order);
        OrderProductEvent orderProductEvent = new OrderProductEvent(SERVICE_NAME, OrderProductEvent.CANCEL_ORDER_EVENT, canceledOrder.getOrderLines());
        streamBridge.send("orderEvent-out-0", orderProductEvent);
        log.info("Cancel order with id = {} successfully", orderId);
        return canceledOrder;
    }

    private boolean checkPaid() {
        return true;
    }
}
