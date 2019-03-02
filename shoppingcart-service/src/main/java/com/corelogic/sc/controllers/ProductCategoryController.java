package com.corelogic.sc.controllers;


import com.corelogic.sc.exceptions.ProductCategoryNotFoundException;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.requests.DeleteProductCategoryRequest;
import com.corelogic.sc.responses.ProductCategoryExceptionResponse;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<ProductCategoryResponse> productCategoryResponses = productCategoryService.getProductCategories();
        return ResponseEntity.ok(productCategoryResponses);
    }

    @GetMapping(value = "/productCategory/{productCategoryName}")
    public ResponseEntity<ProductCategoryResponse> productCategory(@PathVariable("productCategoryName") String productCategoryName)
            throws ProductCategoryNotFoundException {
        ProductCategoryResponse productCategoryResponse = productCategoryService.getProductCategory(productCategoryName);
        return ResponseEntity.ok(productCategoryResponse);
    }

    @PostMapping(value = "/productCategory")
    public ResponseEntity<ProductCategoryResponse> productCategory(@RequestBody AddProductCategoryRequest addProductCategoryRequest) {
        ProductCategoryResponse productCategoryResponse = productCategoryService.addProductCategory(addProductCategoryRequest);
        return ResponseEntity.ok(productCategoryResponse);
    }

    // TODO - immersion 4
    @DeleteMapping(value = "/productCategory")
    public ResponseEntity<ProductCategoryResponse> productCategory(@RequestBody DeleteProductCategoryRequest deleteProductCategoryRequest) {
        return null;
    }

    @ExceptionHandler(ProductCategoryNotFoundException.class)
    public ResponseEntity<ProductCategoryExceptionResponse> productCategoryNotFound(ProductCategoryNotFoundException exception) {
        return new ResponseEntity<>(new ProductCategoryExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
