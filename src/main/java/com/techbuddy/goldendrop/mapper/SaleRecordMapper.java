package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.SaleRecordDTO;
import com.techbuddy.goldendrop.model.SaleRecord;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.SaleRecordRequest;
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
public interface SaleRecordMapper {
    @Mapping(target = "store", source = "store")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    SaleRecord map(SaleRecordRequest saleRecordRequest, Store store, User user);

    SaleRecordDTO map(SaleRecord saleRecord);
}
