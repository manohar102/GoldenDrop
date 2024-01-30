package com.techbuddy.goldendrop.dto;

import com.techbuddy.goldendrop.model.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
}
