package com.techbuddy.goldendrop.security;

import com.techbuddy.goldendrop.model.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class APIToken {

    private final Long userId;
    private final String email;
    private final UserRole role;
}
