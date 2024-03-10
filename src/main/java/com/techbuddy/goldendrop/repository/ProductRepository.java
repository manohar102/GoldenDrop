package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.Product;
import java.util.Optional;

public interface ProductRepository extends BaseRepository<Product, Long> {

    Optional<Product> findProductByIdAndAndStoreId(Long id, Long storeId);
}
