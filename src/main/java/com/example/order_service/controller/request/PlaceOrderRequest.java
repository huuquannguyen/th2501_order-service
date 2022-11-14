package com.example.order_service.controller.request;

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

    @NotNull(message = "Order ids cannot be blank")
    private List<Long> orderIds;

    @Valid
    private RecipientInformationRequest recipientInformation;

}
