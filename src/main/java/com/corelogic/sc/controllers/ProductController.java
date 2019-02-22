package com.corelogic.sc.controllers;


import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.requests.AddProductRequest;
import com.corelogic.sc.responses.ProductCategoryExceptionResponse;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.responses.ProductResponse;
import com.corelogic.sc.services.ProductCategoryService;
import com.corelogic.sc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products/{productCategoryName}")
    public ResponseEntity<List<ProductResponse>> products(@PathVariable("productCategoryName") String productCategoryName) {
        List<ProductResponse> productResponses = productService.getProducts(productCategoryName);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping(value = "/product/{skuNumber}")
    public ResponseEntity<ProductResponse> product(@PathVariable("skuNumber") String skuNumber) throws ProductNotFoundException {
        ProductResponse productResponse = productService.getProductBySkuNumber(skuNumber);
        return ResponseEntity.ok(productResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductCategoryExceptionResponse> productNotFound(ProductNotFoundException exception) {
        return new ResponseEntity<>(new ProductCategoryExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ProductResponse> product(@RequestBody AddProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }
}
