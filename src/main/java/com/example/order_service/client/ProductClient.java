package com.example.order_service.client;

import com.example.order_service.client.response.Product;
import com.example.order_service.model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component("productClient")
@FeignClient("${client.product-service}")
public interface ProductClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}", consumes = "application/json")
    ApiResponse<Product> getProduct(@PathVariable Long id);

}
