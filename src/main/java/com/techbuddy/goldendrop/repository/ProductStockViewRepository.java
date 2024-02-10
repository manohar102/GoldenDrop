package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.ProductStockView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockViewRepository extends JpaRepository<ProductStockView, Long> {
    ProductStockView findProductStockViewById(Long id);
}
