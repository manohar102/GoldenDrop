package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.enums.ProductType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javax.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Valid
@Data
public class ProductRequest {
    @NotBlank(message = "brandName cannot be null")
    private String brandName;

    @NotNull(message = "type cannot be null")
    private ProductType type;

    @NotNull(message = "quantity cannot be null")
    private String quantity;

    @Nullable
    private MultipartFile imageFile;

    @Nullable
    private Long id;
}
