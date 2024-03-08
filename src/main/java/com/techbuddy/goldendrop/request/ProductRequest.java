package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.enums.ProductType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@Getter
@Valid
public class ProductRequest {
    @NotBlank(message = "brandName cannot be null")
    private String brandName;

    @NotNull(message = "type cannot be null")
    private ProductType type;

    @NotNull(message = "storeId cannot be null")
    private Long storeId;

    @NotNull(message = "quantity cannot be null")
    private String quantity;

    @Nullable
    MultipartFile imageFile;

    private Long productId;
}
