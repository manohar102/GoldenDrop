package com.techbuddy.goldendrop.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRecordRequest {
    private Double saleAmount;

    private Double digitalAmount;

    private Double onlineAmount;

    private Double expenses;
    private String comments;

    @NotNull(message = "storeId cannot be null")
    private Long storeId;

    @AssertFalse(message = "comments cannot be empty if any of the given amount is negative")
    @Schema(hidden = true)
    public boolean isCommentsProvided() {
        return ((saleAmount < 0 || digitalAmount < 0 || onlineAmount < 0 || expenses < 0)
                && (comments == null || comments.isBlank()));
    }
}
