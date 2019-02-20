package com.corelogic.sc.controllers;


import com.corelogic.sc.requests.ProductCategoryRequest;
import com.corelogic.sc.requests.ProductRequest;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.responses.ProductResponse;
import com.corelogic.sc.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productCatalog")
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping(value = "/productCategories")
    public ResponseEntity<List<ProductCategoryResponse>> productCategories() {
        List<ProductCategoryResponse> productCategoryResponses =
                productCategoryService.getProductCategories();
        return ResponseEntity.ok(productCategoryResponses);
    }

    @GetMapping(value = "/productCategory/{productCategoryName}")
    public ResponseEntity<ProductCategoryResponse> productCategory(@PathVariable("productCategoryName") String productCategoryName) {
        ProductCategoryResponse productCategoryResponse = productCategoryService.getProductCategory(productCategoryName);
        return ResponseEntity.ok(productCategoryResponse);
    }

    @PostMapping(value = "/productCategory")
    public ResponseEntity<ProductCategoryResponse> productCategory(@RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryResponse productCategoryResponse = productCategoryService.addProductCategory(productCategoryRequest);
        return ResponseEntity.ok(productCategoryResponse);
    }

    @GetMapping(value = "/products/{productCategoryName}")
    public ResponseEntity<List<ProductResponse>> products(@PathVariable("productCategoryName") String productCategoryName) {
        return null;
    }

    @GetMapping(value = "/product/{skuNumber}")
    public ResponseEntity<ProductResponse> product(@PathVariable("skuNumber") String skuNumber) {
        return null;
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ProductResponse> product(@RequestBody ProductRequest productRequest) {
        return null;
    }
}
