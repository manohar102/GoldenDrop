package com.techbuddy.goldendrop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SaleRecordDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private Double saleAmount;
    private Double digitalAmount;
    private Double onlineAmount;
    private Double expenses;
}
