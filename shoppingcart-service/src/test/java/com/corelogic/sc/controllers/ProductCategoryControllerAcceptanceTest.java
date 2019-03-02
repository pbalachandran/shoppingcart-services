package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServiceApplication;
import com.corelogic.sc.requests.AddProductCategoryRequest;
import com.corelogic.sc.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ShoppingCartServiceApplication.class})
public class ProductCategoryControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${db.utilities.url}")
    private String dbUtilitiesURL;

    @Before
    public void setUp() throws Exception {
        restTemplate.exchange(dbUtilitiesURL + "/reseed",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<Void>() {
                });
    }

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
    }

    @Test
    public void productCategory_retrievesProductCategoryByInvalidProductCategoryName_throwsProductCategoryException() throws Exception {
        mockMvc.perform(get("/api/productCatalog/productCategory/InvalidProductCategory")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/product-category-notfound.json")));
    }
}