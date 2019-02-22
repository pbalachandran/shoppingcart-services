package com.corelogic.sc.requests;

import com.corelogic.sc.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
public class AddProductRequest {

    private String skuNumber;

    private String productName;

    private String description;

    private Integer inventoryCount;

    private Double price;

    private String productCategoryName;
}
