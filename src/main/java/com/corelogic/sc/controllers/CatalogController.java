package com.corelogic.sc.controllers;


import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.responses.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@AllArgsConstructor
public class CatalogController {

    @GetMapping(value = "categories")
    public ResponseEntity<List<ProductCategoryResponse>> categories() {
        return null;
    }

    @GetMapping(value = "categories/{categoryName}")
    public ResponseEntity<List<ProductResponse>> products(@PathVariable("categoryName") String categoryName) {
        return null;
    }
}
