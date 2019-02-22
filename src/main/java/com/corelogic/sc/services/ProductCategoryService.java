package com.corelogic.sc.services;

import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.exceptions.ProductCategoryNotFoundException;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public ProductCategoryResponse getProductCategory(String productCategoryName) throws ProductCategoryNotFoundException {
        ProductCategory productCategory = productCategoryRepository.findByProductCategoryName(productCategoryName);

        if(productCategory == null) {
            throw new ProductCategoryNotFoundException("Product Category " + productCategoryName + " was not found");
        }
        return ProductCategoryResponse
                .builder()
                .productCategoryName(productCategory.getProductCategoryName())
                .description(productCategory.getDescription())
                .build();
    }

    public ProductCategoryResponse addProductCategory(AddProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = productCategoryRepository.save(ProductCategory
                .builder()
                .productCategoryName(productCategoryRequest.getProductCategoryName())
                .description(productCategoryRequest.getDescription())
                .createdDate(LocalDateTime.now())
                .build());
        return ProductCategoryResponse
                .builder()
                .productCategoryName(productCategory.getProductCategoryName())
                .description(productCategory.getDescription())
                .build();
    }
}
