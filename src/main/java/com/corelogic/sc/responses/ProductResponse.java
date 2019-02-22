package com.corelogic.sc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private String skuNumber;
    private String productName;
    private String description;
    private Integer inventoryCount;
    private Double price;
}
