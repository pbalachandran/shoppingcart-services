package com.corelogic.sc.controllers;

import com.corelogic.sc.ShoppingCartServicesApplication;
import com.corelogic.sc.entities.Item;
import com.corelogic.sc.requests.AddItemRequest;
import com.corelogic.sc.respositories.ItemRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ShoppingCartServicesApplication.class})
public class ItemControllerAcceptanceTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void item_createsItems() throws Exception {
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

        Item iphone8S = itemRepository.findBySkuNumber("IPHONE8S");
        itemRepository.delete(iphone8S.getItemId());
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