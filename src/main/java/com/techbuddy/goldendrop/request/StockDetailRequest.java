package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.model.StockTransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Valid
@Getter
public class StockDetailRequest {
    @NotNull(message = "productId cannot be null")
    private Long productId;

    @NotNull(message = "productPrice cannot be null")
    @Min(value = 1, message = "productPrice must be greater than zero")
    private Double productPrice;

    @NotNull(message = "stockTransactionType cannot be null")
    private StockTransactionType type;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than zero")
    private Integer quantity;
}
