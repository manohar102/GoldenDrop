package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.exception.InvalidProductException;
import com.techbuddy.goldendrop.mapper.ProductMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.repository.ProductRepository;
import com.techbuddy.goldendrop.request.ProductRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductMapper productMapper;

    public ProductDTO createOrUpdate(ProductRequest productRequest) {

        Store store = storeService.fetchStoreById(productRequest.getStoreId());
        Product productToBeSavedOrUpdated;

        if (productRequest.getProductId() == null) {
            productToBeSavedOrUpdated = productMapper.map(productRequest, store);
        } else {
            Product existingProduct =
                    fetchProductByProductIdAndStoreId(productRequest.getProductId(), productRequest.getStoreId());
            productToBeSavedOrUpdated = productMapper.map(productRequest, store, existingProduct);
        }
        return productMapper.map(productRepository.save(productToBeSavedOrUpdated));
    }

    private Product fetchProductByProductIdAndStoreId(Integer productId, Integer storeId) {
        Optional<Product> product = productRepository.findProductByIdAndAndStoreId(productId, storeId);
        if (product.isEmpty()) {
            throw new InvalidProductException("Invalid productId/StoreId");
        }
        return product.get();
    }
}
