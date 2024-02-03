package com.techbuddy.goldendrop.controller;

import static com.techbuddy.goldendrop.model.Product.buildUpdatedStockDetailsOfAProduct;

import com.techbuddy.goldendrop.controller.request.ProductRequest;
import com.techbuddy.goldendrop.controller.response.ProductResponse;
import com.techbuddy.goldendrop.exception.InvalidProductException;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.repository.ProductRepository;
import com.techbuddy.goldendrop.model.repository.StoreRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private StoreRepository storeRepository;
    private ProductRepository productRepository;
    private StoreService storeService;

    public ProductService(
            StoreRepository storeRepository, ProductRepository productRepository, StoreService storeService) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.storeService = storeService;
    }

    public ProductResponse createOrUpdate(ProductRequest productRequest) {

        Store store = storeService.fetchStoreById(productRequest.getStoreId());
        Product productToBeSavedOrUpdated;

        if (productRequest.getProductId() == null) {
            productToBeSavedOrUpdated = Product.buildFromRequest(productRequest, store);
        } else {
            Product product =
                    fetchProductByProductIdAndStoreId(productRequest.getProductId(), productRequest.getStoreId());
            productToBeSavedOrUpdated = buildUpdatedStockDetailsOfAProduct(productRequest, product);
        }
        productRepository.save(productToBeSavedOrUpdated);
        return ProductResponse.buildFromEntity(productToBeSavedOrUpdated);
    }

    private Product fetchProductByProductIdAndStoreId(Integer productId, Integer storeId) {
        Optional<Product> product = productRepository.findProductByIdAndAndStoreId(productId, storeId);
        if (product.isEmpty()) {
            throw new InvalidProductException("Invalid productId/StoreId");
        }
        return product.get();
    }
}
