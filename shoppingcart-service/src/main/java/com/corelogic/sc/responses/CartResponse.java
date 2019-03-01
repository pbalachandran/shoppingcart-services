package com.corelogic.sc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartResponse {
    private String cartName;
    private String description;
}
