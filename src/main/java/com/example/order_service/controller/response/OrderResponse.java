package com.example.order_service.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String status;
    private Long productId;
    private Integer quantity;
    private String size;
    private Double price;
    private String imageUrl;
    private String address;

}
