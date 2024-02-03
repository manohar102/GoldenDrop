package com.techbuddy.goldendrop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    SUPER_ADMIN,
    ADMIN,
    USER,
}
