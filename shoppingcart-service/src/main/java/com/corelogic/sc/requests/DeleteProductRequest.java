package com.corelogic.sc.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteProductRequest {
    private String skuNumber;

    private String productName;

    private String description;

    private Integer inventoryCount;

    private Double price;

    private String productCategoryName;
}
