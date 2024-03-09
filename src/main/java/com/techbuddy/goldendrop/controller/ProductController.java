package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.request.ProductRequest;
import com.techbuddy.goldendrop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a product")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ProductDTO> createOrUpdate(
            @Valid @ModelAttribute @NotNull(message = "productRequest " + "cannot be " + "null")
                    ProductRequest productRequest) throws IOException {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest));
    }

    @GetMapping
    @Operation(summary = "Get products of a store")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> getProductsByStoreId(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        // TODO - Fetch store id by context
        return ResponseEntity.ok(productService.getProductsByStoreId(storeId, pageNumber, pageSize));
    }

    @GetMapping("/popular")
    @Operation(summary = "Get Top products")
    @ResponseStatus(code = HttpStatus.OK)
    //public and private
    //specification base
    //relations
    public ResponseEntity<List<ProductDTO>> getTopFiveProducts(@RequestParam(required = false) Long storeId) {
        return ResponseEntity.ok(productService.getTopFiveProducts(storeId));
    }
}
