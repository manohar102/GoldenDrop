package com.techbuddy.goldendrop.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "store")
@Data
public class Store extends BaseModel {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String licenseId;
    private String name;
    private String email;
    private String mblNumber;
    private String address;
}
