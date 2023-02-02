package com.example.order_service.client;

import com.example.order_service.client.response.Product;
import com.example.order_service.config.FeignClientConfig;
import com.example.order_service.model.ApiResponse;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component("productClient")
@FeignClient(value = "${client.product-service}", configuration = FeignClientConfig.class)
public interface ProductClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    ApiResponse<Product> getProduct(@PathVariable Long id, @RequestHeader(value = "Authorization") String token);

}
