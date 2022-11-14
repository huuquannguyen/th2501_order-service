package com.example.order_service.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientInformationRequest {

    @NotBlank(message = "Recipient's name cannot be blank")
    private String name;

    @Valid
    private AddressRequest address;

    @Pattern(regexp = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$",
            message = "Phone number is not match the correct format")
    private String phoneNumber;

}
