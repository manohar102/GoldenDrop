package com.techbuddy.goldendrop.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "stock_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetail extends BaseModel {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price")
    private String productPrice;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StockTransactionType type;

    @Column(name = "quantity")
    private int quantity;
}
