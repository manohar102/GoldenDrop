package com.techbuddy.goldendrop.controller.response;

import com.techbuddy.goldendrop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long productId;

    public static ProductResponse buildFromEntity(Product product) {
        return ProductResponse.builder().productId(product.getId()).build();
    }
}
