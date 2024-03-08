package com.techbuddy.goldendrop.model;

import static jakarta.persistence.FetchType.LAZY;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "product")
@Data
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

    private String quantity;

    @Column(name = "image_name")
    private String imageName;
}
