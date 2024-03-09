package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.mapper.ProductMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.ProductRequest;
import com.techbuddy.goldendrop.service.ProductService;
import com.techbuddy.goldendrop.service.UserService;
import com.techbuddy.goldendrop.specification.ProductSpecificationBuilder;
import com.techbuddy.goldendrop.specification.SearchCriteria;
import com.techbuddy.goldendrop.specification.SearchOperation;
import com.techbuddy.goldendrop.specification.UserSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get products of a store")
    public Page<ProductDTO> index(
            ProductSpecificationBuilder builder, @PageableDefault(value = 25, page = 0) Pageable pageable) {
        log.info("Request GET /user");
        User user = userService.fetchUser();
        SearchCriteria criteria = new SearchCriteria(
                "storeId", SearchOperation.EQUALITY, user.getStore().getId());
        builder.params.add(criteria);

        Specification<Product> spec = builder.build();
        Page<Product> products = service.findAll(spec, pageable);
        List<Product> productList = service.setPresignedUrlsForProduct(products.getContent());
        List<ProductDTO> productDTOS = mapper.map(productList);
        return new PageImpl<>(productDTOS, pageable, products.getTotalElements());
    }

    @PostMapping
    @Operation(summary = "Create a product")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ProductDTO> createOrUpdate(
            @Valid @ModelAttribute @NotNull(message = "productRequest " + "cannot be " + "null")
                    ProductRequest productRequest)
            throws IOException {
        return ResponseEntity.ok(service.createOrUpdate(productRequest));
    }

//    @GetMapping
//    @Operation(summary = "Get products of a store")
//    @ResponseStatus(code = HttpStatus.OK)
//    public ResponseEntity<List<ProductDTO>> getProductsByStoreId(
//            @RequestParam Long storeId,
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "10") int pageSize) {
//        // TODO - Fetch store id by context
//        return ResponseEntity.ok(service.getProductsByStoreId(storeId, pageNumber, pageSize));
//    }

    @GetMapping("/popular")
    @Operation(summary = "Get Top products")
    @ResponseStatus(code = HttpStatus.OK)
    // public and private
    // specification base
    // relations
    public ResponseEntity<List<ProductDTO>> getTopFiveProducts(@RequestParam(required = false) Long storeId) {
        return ResponseEntity.ok(service.getTopFiveProducts(storeId));
    }
}
