package com.techbuddy.goldendrop.model;

import static jakarta.persistence.FetchType.LAZY;

import com.techbuddy.goldendrop.controller.request.ProductRequest;
import com.techbuddy.goldendrop.enums.ProductType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @OneToMany(mappedBy = "product", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockDetail> stockDetails;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public static Product buildFromRequest(ProductRequest productRequest, Store store) {
        Product product = Product.builder()
                .brandName(productRequest.getBrandName())
                .type(productRequest.getProductType())
                .store(store)
                .build();

        product.stockDetails = List.of(StockDetail.builder()
                .type(productRequest.getStockTransactionType())
                .productPrice(productRequest.getProductPrice())
                .quantity(productRequest.getQuantity())
                .product(product)
                .build());
        return product;
    }

    public static Product buildUpdatedStockDetailsOfAProduct(ProductRequest productRequest, Product product) {
        product.getStockDetails()
                .add(StockDetail.builder()
                        .productPrice(productRequest.getProductPrice())
                        .quantity(productRequest.getQuantity())
                        .type(productRequest.getStockTransactionType())
                        .product(product)
                        .build());
        return product;
    }
}
