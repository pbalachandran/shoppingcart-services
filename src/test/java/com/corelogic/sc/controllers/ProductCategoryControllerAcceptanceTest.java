package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServicesApplication;
import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.respositories.ProductCategoryRepository;
import com.corelogic.sc.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ShoppingCartServicesApplication.class})
public class ProductCategoryControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void productCategories_retrievesAllCategories() throws Exception {
        mockMvc.perform(get("/api/productCatalog/productCategories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-categories.json")));
    }

    @Test
    public void productCategory_whenSearchedByProductCategoryName_retrievesProductCategory() throws Exception {
        mockMvc.perform(get("/api/productCatalog/productCategory/Electronics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-category.json")));
    }

    @Test
    public void productCategory_createsNewProductCategory() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddProductCategoryRequest
                        .builder()
                        .productCategoryName("Stationary Supplies")
                        .description("Stationary & Paper")
                        .build());

        mockMvc.perform(post("/api/productCatalog/productCategory")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-category-add.json")));

        ProductCategory savedProductCategory = productCategoryRepository.findByProductCategoryName("Stationary Supplies");
        productCategoryRepository.delete(savedProductCategory);
    }
}