package com.corelogic.sc.services;

import com.corelogic.sc.entities.Product;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddProductRequest;
import com.corelogic.sc.requests.DeleteProductRequest;
import com.corelogic.sc.responses.ProductResponse;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import com.corelogic.sc.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductResponse> getProducts(String productCategoryName) {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .filter(product -> product.getProductCategory().getProductCategoryName().equals(productCategoryName)).map(product ->
                        ProductResponse
                                .builder()
                                .skuNumber(product.getSkuNumber())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .inventoryCount(product.getInventoryCount())
                                .price(product.getPrice())
                                .build()).collect(Collectors.toList());
    }

    public ProductResponse getProductBySkuNumber(String skuNumber) throws ProductNotFoundException {
        Product product = productRepository.findBySkuNumber(skuNumber);
        if (product == null) {
            throw new ProductNotFoundException("No product exists for sku# " + skuNumber);
        }

        return ProductResponse
                .builder()
                .skuNumber(product.getSkuNumber())
                .productName(product.getProductName())
                .description(product.getDescription())
                .inventoryCount(product.getInventoryCount())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse addProduct(AddProductRequest addProductRequest) {
        Product product = productRepository.save(Product
                .builder()
                .skuNumber(addProductRequest.getSkuNumber())
                .productName(addProductRequest.getProductName())
                .description(addProductRequest.getDescription())
                .inventoryCount(addProductRequest.getInventoryCount())
                .price(addProductRequest.getPrice())
                .productCategory(productCategoryRepository.findByProductCategoryName(addProductRequest.getProductCategoryName()))
                .createdDate(LocalDateTime.now())
                .build());

        return ProductResponse
                .builder()
                .skuNumber(product.getSkuNumber())
                .productName(product.getProductName())
                .description(product.getDescription())
                .inventoryCount(product.getInventoryCount())
                .price(product.getPrice())
                .build();
    }

    // TODO - immersion 3.1
    public ProductResponse deleteProduct(DeleteProductRequest deleteProductRequest) {
        return null;
    }
}
