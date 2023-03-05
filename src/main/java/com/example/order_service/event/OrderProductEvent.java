package com.example.order_service.event;

import com.example.order_service.entity.OrderLine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductEvent {
    private String source;
    private String eventType;
    private List<OrderLine> orderLines;

    public static final String PLACE_ORDER_EVENT = "PlaceOrderEvent";
    public static final String CANCEL_ORDER_EVENT = "CancelOrderEvent";
}
