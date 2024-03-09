package com.techbuddy.goldendrop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRecord extends BaseModel {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Double saleAmount;

    private Double digitalAmount;

    private Double onlineAmount;
    private Double expenses;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
