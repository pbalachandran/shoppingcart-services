package com.corelogic.sc.controllers;


import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.responses.ProductResponse;
import com.corelogic.sc.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping(value = "categories")
    public ResponseEntity<List<ProductCategoryResponse>> productCategories() {
        List<ProductCategoryResponse> productCategoryResponses =
                productCategoryService.getProductCategories();
        return ResponseEntity.ok(productCategoryResponses);
    }

    @GetMapping(value = "category/{categoryName}")
    public ResponseEntity<ProductCategoryResponse> productCategory(@PathVariable("categoryName") String categoryName) {
        return null;
    }

    @GetMapping(value = "categories/{categoryName}/products")
    public ResponseEntity<List<ProductResponse>> products(@PathVariable("categoryName") String categoryName) {
        return null;
    }
}
