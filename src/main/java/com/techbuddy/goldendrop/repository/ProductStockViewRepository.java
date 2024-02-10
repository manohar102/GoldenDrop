package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.ProductStockView;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ProductStockViewRepository extends BaseRepository<ProductStockView, Long> {
    ProductStockView findProductStockViewById(Long id);

    List<ProductStockView> findProductStockViewByStoreId(Long storeId, Pageable pageable);

    @Query(
            "SELECT psv FROM ProductStockView psv WHERE (:storeId IS NULL OR psv.storeId = :storeId) ORDER BY psv.outQuantity DESC")
    List<ProductStockView> findTop5ByStoreIdOrderByOutQuantityDesc(Long storeId);
}
