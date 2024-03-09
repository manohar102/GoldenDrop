package com.techbuddy.goldendrop.repository;

import com.techbuddy.goldendrop.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends BaseRepository<Product, Long> {

    Optional<Product> findProductByIdAndAndStoreId(Long id, Long storeId);
}
