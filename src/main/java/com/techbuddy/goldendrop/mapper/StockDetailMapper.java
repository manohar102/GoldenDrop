package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.StockDetailDTO;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.StockDetail;
import com.techbuddy.goldendrop.request.StockDetailRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface StockDetailMapper {
    List<StockDetailDTO> map(List<StockDetail> stockDetailList);

    @Mapping(target = "productId", source = "stockDetail.product.id")
    StockDetailDTO map(StockDetail stockDetail);

    @Mapping(target = "type", source = "stockDetailRequest.type")
    @Mapping(target = "quantity", source = "stockDetailRequest.quantity")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    StockDetail map(StockDetailRequest stockDetailRequest, Product product);
}
