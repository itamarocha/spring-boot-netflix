package com.invillia.acme.paymentservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.invillia.acme.paymentservice.web.models.ProductInventoryResponse;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface OrderServiceFeignClient {

    @GetMapping("/api/inventory")
    List<ProductInventoryResponse> getInventoryLevels();

    @GetMapping("/api/inventory/{productCode}")
    List<ProductInventoryResponse> getInventoryByProductCode(String productCode);

}
