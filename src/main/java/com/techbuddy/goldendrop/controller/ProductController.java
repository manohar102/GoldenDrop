package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.request.ProductRequest;
import com.techbuddy.goldendrop.service.ProductService;
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
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create or update product and stock details")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ProductDTO> createOrUpdate(
            @Valid @RequestBody @NotNull(message = "productRequest " + "cannot be " + "null")
                    ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest));
    }
}
