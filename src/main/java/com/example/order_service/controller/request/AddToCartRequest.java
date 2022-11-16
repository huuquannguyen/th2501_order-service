package com.example.order_service.controller.request;

import com.example.order_service.controller.request.constant.SizeCharacter;
import com.example.order_service.controller.request.constant.SizeNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    @Valid
    private SizeRequest sizeRequest;
}
