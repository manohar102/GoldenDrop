package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.StockDetailDTO;
import com.techbuddy.goldendrop.request.StockDetailRequest;
import com.techbuddy.goldendrop.service.StockDetailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/stock-details")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StockDetailController {

    private final StockDetailService stockDetailService;

    @PostMapping
    @Operation(summary = "Create stock details")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<StockDetailDTO>> create(
            @Valid
                    @RequestBody
                    @NotNull(message = "stockDetailsRequest " + "cannot be " + "null")
                    @Size(min = 1, message = "stockDetailsRequest cannot be empty")
                    List<StockDetailRequest> stockDetailsRequest) {
        log.info(
                "Creating stock details for storeId {}",
                stockDetailsRequest.get(0).getStoreId());
        return ResponseEntity.ok(stockDetailService.create(stockDetailsRequest));
    }
}
