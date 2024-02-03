package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.enums.ProductType;
import com.techbuddy.goldendrop.model.StockTransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Valid
public class ProductRequest {
    @NotBlank(message = "brandName cannot be null")
    private String brandName;

    @NotNull(message = "productType cannot be null")
    private ProductType productType;

    @NotNull(message = "storeId cannot be null")
    private int storeId;

    @NotNull(message = "productPrice cannot be null")
    @Min(value = 1, message = "productPrice must be greater than zero")
    private Double productPrice;

    @NotNull(message = "stockTransactionType cannot be null")
    private StockTransactionType stockTransactionType;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than zero")
    private Integer quantity;

    private Integer productId;
}
