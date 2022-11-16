package com.example.order_service.exception;

import com.example.order_service.constant.ErrorCode;
import com.example.order_service.model.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionsHandler {

    private final Log logger = LogFactory.getLog(ExceptionsHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<String> handleError(HttpServletRequest req, MethodArgumentNotValidException e){
        logger.error("Request " + req.getRequestURL() + " raised " + e);
        return ApiResponse.failureWithCode(ErrorCode.VALIDATION_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<String> handleError(HttpServletRequest req, ApiException e) {
        logger.error("Request " + req.getRequestURL() + " raised " + e);
        return ApiResponse.failureWithCode(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<String> handleError(HttpServletRequest req, Exception e) {
        logger.error("Request " + req.getRequestURL() + " raised " + e);
        return ApiResponse.failureWithCode(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
    }
}
