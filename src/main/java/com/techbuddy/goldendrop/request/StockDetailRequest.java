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

    private Double productPrice;

    @NotNull(message = "stockTransactionType cannot be null")
    private StockTransactionType type;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than zero")
    private Integer quantity;

    @AssertFalse(message = "productPrice cannot be null or 0 if stock type is IN")
    @Schema(hidden = true)
    public boolean isProductPricePresent() {
        if(type != null && type.equals(StockTransactionType.IN)) {
            return productPrice != null && productPrice >0;
        }
        return true;
    }
}
