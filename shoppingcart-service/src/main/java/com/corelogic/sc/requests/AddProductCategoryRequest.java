package com.corelogic.sc.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddProductCategoryRequest {

    private String productCategoryName;

    private String description;
}
