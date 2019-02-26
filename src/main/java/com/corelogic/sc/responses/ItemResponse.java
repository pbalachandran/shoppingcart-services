package com.corelogic.sc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemResponse {

    private Integer quantity;

    private String cartName;

    private String skuNumber;

    private Double price;
}
