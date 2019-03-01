package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServiceApplication;
import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.respositories.CartRepository;
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
@ContextConfiguration(classes = {ShoppingCartServiceApplication.class})
public class CartControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void cart_createsNewCart() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(AddCartRequest
                        .builder()
                        .cartName("Cart101")
                        .description("MyFirstCart")
                        .build());

        mockMvc.perform(post("/api/carts/cart")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-add.json")));

        Cart savedCart = cartRepository.findByCartName("Cart101");
        cartRepository.delete(savedCart);
    }

    @Test
    public void cart_retrievesCartByName() throws Exception {
        cartRepository.save(Cart.builder().cartName("Cart101").description("MyFirstCart").build());
        cartRepository.save(Cart.builder().cartName("SecondCart").description("SecondCart").build());
        cartRepository.save(Cart.builder().cartName("ThirdCart").description("ThirdCart").build());

        mockMvc.perform(get("/api/carts/cart/Cart101")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-add.json")));

        cartRepository.delete("Cart101");
        cartRepository.delete("SecondCart");
        cartRepository.delete("ThirdCart");
    }

    @Test
    public void cart_retrieveCartByInvalidCartName_throwsCartNotFoundException() throws Exception {
        mockMvc.perform(get("/api/carts/cart/InvalidCart"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(TestUtils.readFixture("responses/cart-notfound.json"), true));
    }
}