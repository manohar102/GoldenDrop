package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.StoreDTO;
import com.techbuddy.goldendrop.request.StoreRequest;
import com.techbuddy.goldendrop.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @Operation(summary = "Create a store")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<StoreDTO> createOrUpdate(
            @Valid @RequestBody @NotNull(message = "storeRequest " + "cannot be " + "null") StoreRequest storeRequest) {
        return ResponseEntity.ok(storeService.create(storeRequest));
    }
}
