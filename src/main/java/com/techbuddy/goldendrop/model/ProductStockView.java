package com.techbuddy.goldendrop.model;

import com.techbuddy.goldendrop.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockView extends BaseModel {

    @Id
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String quantity;

    private int inQuantity;
    private int outQuantity;
}
