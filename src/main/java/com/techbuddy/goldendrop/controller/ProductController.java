package com.techbuddy.goldendrop.controller;


import com.techbuddy.goldendrop.controller.request.ProductRequest;
import com.techbuddy.goldendrop.controller.response.ProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    public ResponseEntity<ProductResponse> createOrUpdate(@Valid @RequestBody
                                                         @NotNull(message = "productRequest "
                                                             + "cannot be "
                                                             + "null")
                                                         ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createOrUpdate(productRequest));
    }

}
