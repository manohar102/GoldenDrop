package com.techbuddy.goldendrop.model;

import com.techbuddy.goldendrop.enums.ProductType;
import jakarta.persistence.*;
import java.net.URL;
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

    @Column(name = "image_name")
    private String imageName;

    private String quantity;

    private int inQuantity;
    private int outQuantity;
    private Long storeId;

    @Transient
    private URL url;
}
