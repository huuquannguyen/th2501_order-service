package com.example.order_service.controller.request;

import com.example.order_service.controller.request.constant.AddressType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotBlank(message = "Province cannot be blank")
    private String province;

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotNull(message = "Address type cannot be null")
    private AddressType addressType;

    private String moreDetail;

    @Override
    public String toString() {
        return "state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", street='" + street + '\'' +
                ", moreDetail='" + moreDetail + '\'';
    }
}
