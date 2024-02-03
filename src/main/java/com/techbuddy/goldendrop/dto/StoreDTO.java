package com.techbuddy.goldendrop.dto;

import com.techbuddy.goldendrop.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreDTO {
    private Long id;
    private String licenseId;
}
