package com.corelogic.sc.services;

import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {


    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryResponse> getProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        return productCategories.stream().map(productCategory ->
                ProductCategoryResponse
                        .builder()
                        .productCategoryName(productCategory.getProductCategoryName())
                        .description(productCategory.getDescription())
                        .build()).collect(Collectors.toList());
    }
}
