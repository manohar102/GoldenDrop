package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.exception.InvalidProductException;
import com.techbuddy.goldendrop.mapper.ProductMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.ProductStockView;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.repository.ProductRepository;
import com.techbuddy.goldendrop.repository.ProductStockViewRepository;
import com.techbuddy.goldendrop.request.ProductRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductMapper productMapper;
    private final ProductStockViewRepository productStockViewRepository;

    public ProductDTO createOrUpdate(ProductRequest productRequest) {

        Store store = storeService.validateAndFetchStoreId(productRequest.getStoreId());
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

    public Product fetchProductByProductIdAndStoreId(Long productId, Long storeId) {
        Optional<Product> product = productRepository.findProductByIdAndAndStoreId(productId, storeId);
        if (product.isEmpty()) {
            throw new InvalidProductException("Invalid productId/StoreId");
        }
        return product.get();
    }

    public List<ProductDTO> getProductsByStoreId(Long storeId, int pageNumber, int pageSize) {
        storeService.validateAndFetchStoreId(storeId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ProductStockView> products = productStockViewRepository.findProductStockViewByStoreId(storeId, pageable);
        return products.stream().map(productMapper::map).toList();
    }

    public List<ProductDTO> getTopFiveProducts(Long storeId) {
        if (storeId != null) {
            storeService.validateAndFetchStoreId(storeId);
        }

        List<ProductStockView> products = productStockViewRepository.findTop5ByStoreIdOrderByOutQuantityDesc(storeId);
        return products.stream().map(productMapper::map).toList();
    }
}
