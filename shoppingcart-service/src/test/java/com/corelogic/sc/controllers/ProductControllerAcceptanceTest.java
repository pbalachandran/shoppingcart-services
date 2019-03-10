package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServiceApplication;
import com.corelogic.sc.requests.AddProductRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ShoppingCartServiceApplication.class})
public class ProductControllerAcceptanceTest {

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
    public void products_retrievesProductsByCategoryName() throws Exception {
        mockMvc.perform(get("/api/products/Electronics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/products-by-category.json")));
    }

    @Test
    public void product_retrieveProductBySkuNumber() throws Exception {
        mockMvc.perform(get("/api/products/product/IPHONE8S")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-by-sku.json")));
    }

    @Test
    public void product_retrieveProductByInvalidSkuNumber_throwsProductNotFoundException() throws Exception {
        mockMvc.perform(get("/api/products/product/INVALIDSKU"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/product-sku-notfound.json"), true));
    }

    @Test
    public void product_createsNewProduct() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddProductRequest
                        .builder()
                        .skuNumber("GALAXY5S")
                        .productName("Galaxy 5S")
                        .description("Galaxy 5S")
                        .inventoryCount(100)
                        .price(799.99)
                        .productCategoryName("Electronics")
                        .build());

        mockMvc.perform(post("/api/products/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-add.json")));
    }

    // TODO: immersion - 1.1
    @Test
    public void product_deleteProduct() {
    }
}