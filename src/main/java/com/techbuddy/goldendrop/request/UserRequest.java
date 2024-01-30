package com.techbuddy.goldendrop.request;

import com.techbuddy.goldendrop.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
