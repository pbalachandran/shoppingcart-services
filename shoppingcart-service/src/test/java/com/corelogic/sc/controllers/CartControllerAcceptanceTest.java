package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServiceApplication;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.requests.DeleteCartRequest;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ShoppingCartServiceApplication.class})
public class CartControllerAcceptanceTest {

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
    public void cart_createsNewCart() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddCartRequest
                        .builder()
                        .cartName("Cart1")
                        .description("Cart1")
                        .build());

        mockMvc.perform(post("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-add.json")));
    }

    @Test
    public void cart_retrievesCartByName() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddCartRequest
                        .builder()
                        .cartName("Cart1")
                        .description("Cart1")
                        .build());

        mockMvc.perform(post("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());

        jsonPayload =
                new ObjectMapper().writeValueAsString(AddCartRequest
                        .builder()
                        .cartName("Cart2")
                        .description("Cart2")
                        .build());

        mockMvc.perform(post("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());

        jsonPayload =
                new ObjectMapper().writeValueAsString(AddCartRequest
                        .builder()
                        .cartName("Cart3")
                        .description("Cart3")
                        .build());

        mockMvc.perform(post("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/carts/cart/Cart1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-add.json")));
    }

    @Test
    public void cart_retrieveCartByInvalidCartName_throwsCartNotFoundException() throws Exception {
        mockMvc.perform(get("/api/carts/cart/InvalidCart"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-notfound.json"), true));
    }

    @Test
    public void cart_deleteCartByName_deletesCart() throws Exception{
        String jsonPayload =
                new ObjectMapper().writeValueAsString(DeleteCartRequest
                        .builder()
                        .cartName("MyFirstCart")
                        .build());

        mockMvc.perform(delete("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    public void cart_deleteCartByInvalidCartName_throwsCartNotFoundException() throws Exception{
        String jsonPayload =
                new ObjectMapper().writeValueAsString(DeleteCartRequest
                        .builder()
                        .cartName("InvalidCart")
                        .build());

        mockMvc.perform(delete("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-notfound.json"), true));
    }
}