package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String phoneNumber;
    private String password;
    private UserRole role;
    private Long storeId;
}
