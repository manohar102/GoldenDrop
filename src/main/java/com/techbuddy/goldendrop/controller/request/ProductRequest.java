package com.techbuddy.goldendrop.controller.request;

import com.techbuddy.goldendrop.enums.ProductType;
import com.techbuddy.goldendrop.model.StockTransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Getter
public class ProductRequest {
    @NotNull(message = "brandName cannot be null")
    private String brandName;
    @NotNull(message = "productType cannot be null")
    private ProductType productType;
    @NotNull(message = "storeId cannot be null")
    private int storeId;
    @NotNull(message = "productPrice cannot be null")
    private String productPrice;
    @NotNull(message = "stockTransactionType cannot be null")
    private StockTransactionType stockTransactionType;
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;
    private Integer productId;
}
