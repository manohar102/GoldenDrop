package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.controller.request.ProductRequest;
import com.techbuddy.goldendrop.controller.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create or update product and stock details")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ProductResponse> createOrUpdate(
            @Valid @RequestBody @NotNull(message = "productRequest " + "cannot be " + "null")
                    ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest));
    }
}
