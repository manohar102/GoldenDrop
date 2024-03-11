package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.ProductStockView;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ProductStockViewRepository extends BaseRepository<ProductStockView, Long> {
    ProductStockView findProductStockViewById(Long id);

    @Query(
            "SELECT psv FROM ProductStockView psv WHERE (:storeId IS NULL OR psv.storeId = :storeId) ORDER BY psv.outQuantity DESC limit 5")
    List<ProductStockView> findTop5ByStoreIdOrderByOutQuantityDesc(Long storeId);

    @Query("SELECT psv FROM ProductStockView psv ORDER BY psv.outQuantity DESC limit 5")
    List<ProductStockView> findTop5ByOrderByOutQuantityDesc();
}
