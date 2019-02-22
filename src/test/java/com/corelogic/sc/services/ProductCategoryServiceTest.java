package com.corelogic.sc.services;

import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.responses.ProductCategoryResponse;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class ProductCategoryServiceTest {

    private ProductCategoryService subject;

    @Mock
    private ProductCategoryRepository mockProductCategoryRepository;

    @Before
    public void setUp() throws Exception {
        subject = new ProductCategoryService(mockProductCategoryRepository);
    }

    @Test
    public void getProductCategories_returnsAllProductCategories() {
        when(mockProductCategoryRepository.findAll()).thenReturn(Arrays.asList(
                ProductCategory
                        .builder()
                        .productCategoryName("Electronics")
                        .description("Electronics & Computer Equipment")
                        .build(),
                ProductCategory
                        .builder()
                        .productCategoryName("Lawn Equipment")
                        .description("Lawn & Garden Equipment")
                        .build()));


        List<ProductCategoryResponse> productCategoryResponses = new ArrayList<>();
        productCategoryResponses.add(ProductCategoryResponse
                .builder()
                .productCategoryName("Electronics")
                .description("Electronics & Computer Equipment")
                .build());

        productCategoryResponses.add(ProductCategoryResponse
                .builder()
                .productCategoryName("Lawn Equipment")
                .description("Lawn & Garden Equipment")
                .build());

        List<ProductCategoryResponse> actual = subject.getProductCategories();

        assertEquals(productCategoryResponses, actual);
    }

    @Test
    public void getProductCategory_whenRetrievedByProductCategoryName_returnsProductCategory() {
        when(mockProductCategoryRepository.findByProductCategoryName("Electronics")).thenReturn(
                ProductCategory
                        .builder()
                        .productCategoryName("Electronics")
                        .description("Electronics & Computer Equipment")
                        .build());

        ProductCategoryResponse productCategoryResponse  = ProductCategoryResponse
                .builder()
                .productCategoryName("Electronics")
                .description("Electronics & Computer Equipment")
                .build();

        ProductCategoryResponse actual = subject.getProductCategory("Electronics");

        assertEquals(productCategoryResponse, actual);
    }

    @Test
    public void addProductCategory_addsProductCategory() {
        ProductCategory productCategory = ProductCategory
                .builder()
                .productCategoryName("Stationary Supplies")
                .description("Stationary & Paper")
                .createdDate(LocalDateTime.now())
                .build();
        when(mockProductCategoryRepository.save(any(ProductCategory.class))).thenReturn(productCategory);

        ProductCategoryResponse productCategoryResponse = ProductCategoryResponse
                .builder()
                .productCategoryName("Stationary Supplies")
                .description("Stationary & Paper")
                .build();

        ProductCategoryResponse actual = subject.addProductCategory(AddProductCategoryRequest
                .builder()
                .productCategoryName("Stationary Supplies")
                .description("Stationary & Paper")
                .build());

        assertEquals(productCategoryResponse, actual);
    }
}