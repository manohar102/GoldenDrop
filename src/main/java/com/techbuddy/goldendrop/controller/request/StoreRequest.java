package com.techbuddy.goldendrop.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Valid
public class StoreRequest {
    @NotBlank(message = "licenseId cannot be empty")
    private String licenseId;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "email cannot be empty")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotBlank(message = "address cannot be empty")
    private String address;
}
