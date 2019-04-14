package com.corelogic.sc.services;

import com.corelogic.sc.entities.Product;
import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddProductRequest;
import com.corelogic.sc.responses.ProductResponse;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import com.corelogic.sc.respositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private ProductCategoryRepository mockProductCategoryRepository;

    private ProductService subject;

    @Before
    public void setUp() throws Exception {
        subject = new ProductService(mockProductRepository, mockProductCategoryRepository);
    }

    @Test
    public void getProducts_returnsAllProductsInProductCategory() {

        when(mockProductRepository.findAll()).thenReturn(Arrays.asList(
                Product
                        .builder()
                        .skuNumber("sku1")
                        .productName("product1")
                        .description("product1 description")
                        .inventoryCount(100)
                        .price(199.99)
                        .createdDate(LocalDateTime.now())
                        .productCategory(ProductCategory
                                .builder()
                                .productCategoryName("Electronics")
                                .description("Electronics").build()).build(),
                Product
                        .builder()
                        .skuNumber("sku2")
                        .productName("product2")
                        .description("product2 description")
                        .inventoryCount(200)
                        .price(299.99)
                        .createdDate(LocalDateTime.now())
                        .productCategory(ProductCategory
                                .builder()
                                .productCategoryName("Lawn & Garden")
                                .description("Lawn & Garden").build()).build()));

        List<ProductResponse> expected = Collections.singletonList(ProductResponse
                .builder()
                .skuNumber("sku1")
                .productName("product1")
                .description("product1 description")
                .inventoryCount(100)
                .price(199.99)
                .build());
        List<ProductResponse> actual = subject.getProducts("Electronics");

        verify(mockProductRepository).findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void getProducts_returnsNoProductsWhenNonExistInProductCategory() {
        when(mockProductRepository.findAll()).thenReturn(Arrays.asList(
                Product
                        .builder()
                        .skuNumber("sku1")
                        .productName("product1")
                        .description("product1 description")
                        .inventoryCount(100)
                        .price(199.99)
                        .createdDate(LocalDateTime.now())
                        .productCategory(ProductCategory
                                .builder()
                                .productCategoryName("Electronics")
                                .description("Electronics").build()).build(),
                Product
                        .builder()
                        .skuNumber("sku2")
                        .productName("product2")
                        .description("product2 description")
                        .inventoryCount(200)
                        .price(299.99)
                        .createdDate(LocalDateTime.now())
                        .productCategory(ProductCategory
                                .builder()
                                .productCategoryName("Lawn & Garden")
                                .description("Lawn & Garden").build()).build()));

        List<ProductResponse> actual = subject.getProducts("Sporting Goods");

        assertEquals(Collections.EMPTY_LIST, actual);
    }

    @Test
    public void getProductsBySkuNumber_returnsProduct() throws ProductNotFoundException {

        when(mockProductRepository.findBySkuNumber("sku1")).thenReturn(Product
                .builder()
                .skuNumber("sku1")
                .productName("product1")
                .description("product1 description")
                .inventoryCount(100)
                .price(199.99)
                .createdDate(LocalDateTime.now())
                .productCategory(ProductCategory
                        .builder()
                        .productCategoryName("Electronics")
                        .description("Electronics").build()).build());

        ProductResponse expected = ProductResponse
                .builder()
                .skuNumber("sku1")
                .productName("product1")
                .description("product1 description")
                .inventoryCount(100)
                .price(199.99)
                .build();

        ProductResponse actual = subject.getProductBySkuNumber("sku1");

        assertEquals(expected, actual);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductsBySkuNumber_whenProductNotFound_throwsProductNotFoundException() throws ProductNotFoundException {
        when(mockProductRepository.findBySkuNumber("Invalid SKU#")).thenReturn(null);

        subject.getProductBySkuNumber("Invalid SKU#");

        verify(mockProductRepository).findBySkuNumber("Invalid SKU#");
    }

    @Test
    public void addProduct_addsNewProduct() {

        ProductCategory productCategory = ProductCategory
                .builder()
                .productCategoryName("Electronics")
                .description("Electronics & Computers")
                .build();

        Product product = Product
                .builder()
                .skuNumber("IPHONE10S")
                .productName("iPhone 10S")
                .description("iPhone 10S")
                .inventoryCount(100)
                .price(1000.99)
                .productCategory(productCategory)
                .build();

        when(mockProductCategoryRepository.findByProductCategoryName("Electronics")).thenReturn(productCategory);
        when(mockProductRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse actual = subject.addProduct(AddProductRequest
                .builder()
                .skuNumber("IPHONE10S")
                .productName("iPhone 10S")
                .description("iPhone 10S")
                .inventoryCount(100)
                .price(1000.99)
                .productCategoryName("Electronics")
                .build());

        ProductResponse expected = ProductResponse
                .builder()
                .skuNumber("IPHONE10S")
                .productName("iPhone 10S")
                .description("iPhone 10S")
                .inventoryCount(100)
                .price(1000.99)
                .build();
        assertEquals(expected, actual);
    }
}