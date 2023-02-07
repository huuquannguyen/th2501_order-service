package com.example.order_service.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    @Valid
    private SizeRequest sizeRequest;

    private String userId;
}
