package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.StockDetail;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.request.ProductRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {
    ProductDTO map(Product product);

    Product map(ProductRequest productRequest, Store store, List<StockDetail> stockDetailList);
}
