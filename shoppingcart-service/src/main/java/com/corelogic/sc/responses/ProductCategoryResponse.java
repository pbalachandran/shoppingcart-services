package com.corelogic.sc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductCategoryResponse {

    private String productCategoryName;

    private String description;
}
