package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.StockDetailDTO;
import com.techbuddy.goldendrop.mapper.StockDetailMapper;
import com.techbuddy.goldendrop.model.StockDetail;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.StockDetailRequest;
import com.techbuddy.goldendrop.service.StockDetailService;
import com.techbuddy.goldendrop.service.UserService;
import com.techbuddy.goldendrop.specification.StockDetailsSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock_details")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StockDetailController {

    private final StockDetailService service;
    private final StockDetailMapper mapper;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get Sale Records of a product")
    public Page<StockDetailDTO> index(
            StockDetailsSpecificationBuilder builder, @PageableDefault(value = 25, page = 0) Pageable pageable) {
        log.info("Request GET /stock_details");
        Specification<StockDetail> spec = builder.build();
        Page<StockDetail> stockDetails = service.findAll(spec, pageable);
        List<StockDetailDTO> stockDetailDTOS = mapper.map(stockDetails.getContent());
        return new PageImpl<>(stockDetailDTOS, pageable, stockDetails.getTotalElements());
    }

    @PostMapping
    @Operation(summary = "Create stock details")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<StockDetailDTO>> create(
            @Valid
                    @RequestBody
                    @NotNull(message = "stockDetailsRequest " + "cannot be " + "null")
                    @Size(min = 1, message = "stockDetailsRequest cannot be empty")
                    List<StockDetailRequest> stockDetailsRequest) {

        User user = userService.fetchUser();
        Store store = user.getStore();

        log.info("Creating stock details for storeId {}", store.getId());
        return ResponseEntity.ok(service.create(stockDetailsRequest));
    }
}
