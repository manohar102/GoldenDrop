package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.StoreDTO;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.request.StoreRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface StoreMapper {
    StoreDTO map(Store store);

    Store map(StoreRequest storeRequest);
}
