package com.techbuddy.goldendrop.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE,
    NOT_ACTIVE,
    DELETED
}
