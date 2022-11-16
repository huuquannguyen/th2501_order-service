package com.example.order_service.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", ""),

    ORDER_NOT_EXIST("ORDER_NOT_EXIST", "Order is not exist"),

    SIZE_NOT_ENOUGH("SIZE_NOT_ENOUGH", "This size for the product doesn't have enought quantity"),

    PRODUCT_OUT_OF_SIZE("PRODUCT_OUT_OF_SIZE", "The quantity of this product's size is 0"),

    SIZE_NOT_EXIST("SIZE_NOT_EXIST", "The size is not exist"),

    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Cannot find the product"),

    UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown error");

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
