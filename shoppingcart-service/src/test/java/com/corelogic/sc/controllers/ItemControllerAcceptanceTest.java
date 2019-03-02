package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServiceApplication;
import com.corelogic.sc.requests.AddItemRequest;
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
public class ItemControllerAcceptanceTest {

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
    public void item_createsItems_decrementsProductInventoryCount() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddItemRequest
                        .builder()
                        .cartName("MyFirstCart")
                        .skuNumber("IPHONE8S")
                        .quantity(2)
                        .build());

        mockMvc.perform(post("/api/items/item")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/item-add.json")));

        mockMvc.perform(get("/api/products/product/IPHONE8S")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/product-by-sku-inventorychange.json")));
    }

    @Test
    public void item_retrieveCartByInvalidCartName_throwsCartNotFoundException() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddItemRequest
                        .builder()
                        .cartName("InvalidCart")
                        .skuNumber("IPHONE8S")
                        .quantity(2)
                        .build());

        mockMvc.perform(post("/api/items/item")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-notfound.json")));
    }

    @Test
    public void item_retrieveProductByInvalidSkuNumber_throwsProductNotFoundException() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddItemRequest
                        .builder()
                        .cartName("MyFirstCart")
                        .skuNumber("INVALIDSKU")
                        .quantity(2)
                        .build());

        mockMvc.perform(post("/api/items/item")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/product-sku-notfound.json")));
    }

    @Test
    public void items_retrieveItemsByCartName() throws Exception {
        mockMvc.perform(get("/api/items/MyFirstCart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/items-by-cartname.json"), true));
    }

    @Test
    public void items_retrieveCartByInvalidCartName_throwsCartNotFoundException() throws Exception {
        mockMvc.perform(get("/api/items/InvalidCart"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-notfound.json"), true));
    }
}