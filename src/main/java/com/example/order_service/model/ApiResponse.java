package com.example.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    T result;
    String errorCode;
    Object message;
    int responseCode;

    public static <T> ApiResponse<T> successWithResult(T result){
        return new ApiResponse<>(result, null, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message){
        return new ApiResponse<>(null, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message, T result){
        return new ApiResponse<>(result, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

}
