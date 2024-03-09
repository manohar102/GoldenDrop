package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.ProductStockView;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {
    ProductDTO map(Product product);

    ProductDTO map(ProductStockView product);

    @Mapping(target = "store", source = "store")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Product map(ProductRequest productRequest, Store store);

    @Mapping(target = "store", source = "store")
    @Mapping(target = "id", source = "existingProduct.id")
    @Mapping(target = "stockDetails", source = "existingProduct.stockDetails")
    @Mapping(target = "createdDate", source = "existingProduct.createdDate")
    @Mapping(target = "imageName", source = "existingProduct.imageName")
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "brandName", source = "productRequest.brandName")
    @Mapping(target = "type", source = "productRequest.type")
    @Mapping(target = "quantity", source = "productRequest.quantity")
    Product map(ProductRequest productRequest, Store store, Product existingProduct);

    List<ProductDTO> map(List<Product> content);
}
