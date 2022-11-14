package com.example.order_service.controller.request.constant;

import lombok.Getter;

@Getter
public enum AddressType {

    HOME("home"),
    COMPANY("company");

    private String type;

    AddressType(String type){
        this.type = type;
    }

}
