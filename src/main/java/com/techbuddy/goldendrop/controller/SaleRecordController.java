package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.SaleRecordDTO;
import com.techbuddy.goldendrop.exception.InvalidRequestException;
import com.techbuddy.goldendrop.request.SaleRecordRequest;
import com.techbuddy.goldendrop.service.SaleRecordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
@Validated
public class SaleRecordController {
    private final SaleRecordService saleRecordService;

    @PostMapping
    @Operation(summary = "Create sale record")
    @ResponseStatus(HttpStatus.OK)
    public SaleRecordDTO create(@Valid @RequestBody SaleRecordRequest saleRecordRequest) {
        return saleRecordService.create(saleRecordRequest);
    }

    @GetMapping
    @Operation(summary = "Get sale records in a specific date interval")
    @ResponseStatus(HttpStatus.OK)
    public SaleRecordDTO getSaleRecordsInAnInterval(@RequestParam(value = "from",
                                                                 required = false) Timestamp from,
                                                   @RequestParam(value = "to",
                                                                 required = false) Timestamp to) {
        if((Objects.isNull(from) && Objects.isNull(to)) || (Objects.nonNull(from) && Objects.nonNull(to))) {
            return saleRecordService.getSaleRecordInAnInterval(from, to);
        }
        throw new InvalidRequestException("Both from and to should be either present or absent");
    }
}
