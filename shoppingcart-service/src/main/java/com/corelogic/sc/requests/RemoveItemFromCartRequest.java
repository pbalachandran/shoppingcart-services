package com.corelogic.sc.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RemoveItemFromCartRequest {
    private Integer quantity;

    private String cartName;

    private String skuNumber;
}
