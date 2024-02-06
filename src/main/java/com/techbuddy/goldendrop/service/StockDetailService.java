package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.dto.StockDetailDTO;
import com.techbuddy.goldendrop.mapper.StockDetailMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.StockDetail;
import com.techbuddy.goldendrop.repository.StockDetailRepository;
import com.techbuddy.goldendrop.request.StockDetailRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockDetailService {

    private final StockDetailMapper stockDetailMapper;
    private final ProductService productService;
    private final StockDetailRepository stockDetailRepository;

    public List<StockDetailDTO> create(List<StockDetailRequest> stockDetailsRequest) {

        List<StockDetail> stockDetailListToBeSaved = stockDetailsRequest.stream()
                .map(request -> {
                    Product product = productService.fetchProductByProductIdAndStoreId(
                            request.getProductId(), request.getStoreId());
                    return stockDetailMapper.map(request, product);
                })
                .toList();

        // TODO - Flatten the in & out in product and validate it with out being saved in stock
        // detail
        stockDetailRepository.saveAll(stockDetailListToBeSaved);

        return stockDetailMapper.map(stockDetailListToBeSaved);
    }
}
