package com.techbuddy.goldendrop.model;

import com.techbuddy.goldendrop.controller.request.StoreRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseModel {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseId;
    private String name;
    private String email;
    private String address;

    public static Store buildFromRequest(StoreRequest storeRequest) {
        return Store.builder()
                .address(storeRequest.getAddress())
                .licenseId(storeRequest.getLicenseId())
                .name(storeRequest.getName())
                .email(storeRequest.getEmail())
                .build();
    }
}
