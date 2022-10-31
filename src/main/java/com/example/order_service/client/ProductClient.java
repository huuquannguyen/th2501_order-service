package com.example.order_service.client;

import com.example.order_service.client.response.Product;
import com.example.order_service.constant.ErrorCode;
import com.example.order_service.exception.ApiException;
import com.example.order_service.model.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("productClient")
@Log4j2
public class ProductClient extends ProviderClient{

    @Value("${client.product.end-point}")
    private String productEndpoint;

    private final ObjectMapper mapper;

    @Autowired
    public ProductClient(RestTemplate restTemplate, ObjectMapper mapper) {
        super(restTemplate);
        this.mapper = mapper;
    }

    @Override
    protected void configDefaultHttpHeaders() {

    }

    @Override
    protected void configDefaultParams() {

    }

    public Product getProduct(Long productId) throws ApiException {
        ApiResponse<?> response = this.sendGet(productEndpoint + "/products/" + productId, ApiResponse.class);
        if(response.getResponseCode() != 200){
            log.info("Get product failed with id {}", productId);
            throw new ApiException(ErrorCode.UNKNOWN_ERROR.getCode(), "Get product failed");
        }
        var result = response.getResult();
        try{
            return mapper.convertValue(result, Product.class);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
