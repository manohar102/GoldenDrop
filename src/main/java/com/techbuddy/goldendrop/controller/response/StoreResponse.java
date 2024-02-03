package com.techbuddy.goldendrop.controller.response;

import com.techbuddy.goldendrop.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreResponse {
    private Long storeId;
    private String licenseId;

    public static StoreResponse buildFromEntity(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .licenseId(store.getLicenseId())
                .build();
    }
}
