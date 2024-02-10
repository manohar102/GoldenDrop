package com.techbuddy.goldendrop.dto;

import com.techbuddy.goldendrop.model.UserRole;
import com.techbuddy.goldendrop.model.UserStatus;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String phoneNumber;
    private UserRole role;
    private UserStatus status;
}
