package com.techbuddy.goldendrop.model.repository;

import com.techbuddy.goldendrop.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductById(Integer id);
}
