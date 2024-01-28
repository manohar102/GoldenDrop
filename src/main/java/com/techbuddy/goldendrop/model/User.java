package com.techbuddy.goldendrop.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "users")
@Data
@ToString
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Boolean getIsDeleted() {
        return true;
    }
}
