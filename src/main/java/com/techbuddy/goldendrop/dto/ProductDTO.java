package com.techbuddy.goldendrop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techbuddy.goldendrop.enums.ProductType;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String brandName;
    private ProductType type;
    private String quantity;
    private int inQuantity;
    private int outQuantity;
    private URL url;
}
