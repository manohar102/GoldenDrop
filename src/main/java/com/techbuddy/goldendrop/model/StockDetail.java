package com.techbuddy.goldendrop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "stock_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetail extends BaseModel {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Double productPrice;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StockTransactionType type;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
