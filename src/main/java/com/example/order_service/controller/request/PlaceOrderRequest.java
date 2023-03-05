package com.example.order_service.controller.request;

import com.example.order_service.constant.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {

    private String userId;

    @NotNull(message = "OrderLine ids cannot be blank")
    private List<Long> orderLineIds;

    @Valid
    private RecipientInformationRequest recipientInformation;

    private PaymentMethod paymentMethod;

}
