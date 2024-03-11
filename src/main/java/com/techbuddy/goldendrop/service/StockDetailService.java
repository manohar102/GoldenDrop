package com.techbuddy.goldendrop.service;

import static com.techbuddy.goldendrop.model.StockTransactionType.isOutStock;

import com.techbuddy.goldendrop.dto.StockDetailDTO;
import com.techbuddy.goldendrop.exception.InvalidStockQuantityException;
import com.techbuddy.goldendrop.mapper.StockDetailMapper;
import com.techbuddy.goldendrop.model.*;
import com.techbuddy.goldendrop.repository.ProductStockViewRepository;
import com.techbuddy.goldendrop.repository.StockDetailRepository;
import com.techbuddy.goldendrop.request.StockDetailRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockDetailService {

    private final StockDetailMapper stockDetailMapper;
    private final ProductService productService;
    private final StockDetailRepository stockDetailRepository;
    private final ProductStockViewRepository productStockViewRepository;
    private final UserService userService;

    public Page<StockDetail> findAll(Specification<StockDetail> specification, Pageable pageable) {
        return stockDetailRepository.findAll(specification, pageable);
    }

    public List<StockDetailDTO> create(List<StockDetailRequest> stockDetailsRequest) {

        User user = userService.fetchUser();
        Store store = user.getStore();

        List<StockDetail> stockDetailListToBeSaved = stockDetailsRequest.stream()
                .map(request -> {
                    Product product =
                            productService.fetchProductByProductIdAndStoreId(request.getProductId(), store.getId());
                    validateStockQuantity(request, product);
                    return stockDetailMapper.map(request, product, user);
                })
                .toList();

        stockDetailRepository.saveAll(stockDetailListToBeSaved);
        return stockDetailMapper.map(stockDetailListToBeSaved);
    }

    private void validateStockQuantity(StockDetailRequest request, Product product) {
        if (isOutStock(request.getType())) {
            ProductStockView productStockView = productStockViewRepository.findProductStockViewById(product.getId());
            throwExceptionIfGivenStockQuantityIsGreaterThanTotalInQuantity(request, productStockView);
        }
    }

    private void throwExceptionIfGivenStockQuantityIsGreaterThanTotalInQuantity(
            StockDetailRequest request, ProductStockView productStockView) {
        if (request.getQuantity() + productStockView.getOutQuantity() > productStockView.getInQuantity()) {
            throw new InvalidStockQuantityException("OUT quantity cannot be "
                    + "greater than IN "
                    + "quantity for the given "
                    + "productId: " + request.getProductId());
        }
    }
}
