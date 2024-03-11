package com.techbuddy.goldendrop.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class StockDetailDTO {
    private Long id;
    private Double productPrice;
    private int quantity;
    public Timestamp createdDate;
    private Long productId;
    private UserDTO user;
}
