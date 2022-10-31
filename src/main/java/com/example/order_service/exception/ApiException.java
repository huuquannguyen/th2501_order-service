package com.example.order_service.exception;

import com.example.order_service.constant.ErrorCode;
import lombok.Data;

@Data
public class ApiException extends Exception{
    private String errorMsg;
    private String errorCode;
    private Object result;

    public ApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public ApiException(String code, String message, Object result){
        super(message);
        this.errorCode = code;
        this.errorMsg = message;
        this.result = result;
    }

    public ApiException(String code, String message){
        super(message);
        this.errorCode = code;
        this.errorMsg = message;
    }

}
