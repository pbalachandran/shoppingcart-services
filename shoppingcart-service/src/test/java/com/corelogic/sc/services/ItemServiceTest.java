package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.entities.Item;
import com.corelogic.sc.entities.Product;
import com.corelogic.sc.entities.ProductCategory;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddItemRequest;
import com.corelogic.sc.responses.ItemResponse;
import com.corelogic.sc.respositories.CartRepository;
import com.corelogic.sc.respositories.ItemRepository;
import com.corelogic.sc.respositories.ProductRepository;
import com.sun.jdi.connect.Connector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    private ItemService subject;

    @Mock
    private ItemRepository mockItemRepository;

    @Mock
    private CartRepository mockCartRepository;

    @Mock
    private ProductRepository mockProductRepository;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private Cart savedCart;

    private Product savedProduct1, savedProduct2;

    private ProductCategory savedProductCategory;

    private Item savedItem;

    private Item item1, item2;

    @Before
    public void setUp() throws Exception {
        subject = new ItemService(mockItemRepository, mockCartRepository, mockProductRepository);

        LocalDateTime now = LocalDateTime.now();

        savedCart = Cart
                .builder()
                .cartName("MyFirstCart")
                .description("My First Cart")
                .items(Collections.EMPTY_LIST)
                .createdDate(now)
                .build();

        savedProductCategory = ProductCategory
                .builder()
                .productCategoryName("Electronics")
                .description("Electronics & Computers")
                .createdDate(now)
                .build();

        savedProduct1 = Product
                .builder()
                .productName("iPhone8S")
                .skuNumber("22")
                .inventoryCount(100)
                .description("iPhone 8S")
                .price(799.99)
                .productCategory(savedProductCategory)
                .createdDate(now)
                .build();

        savedProduct2 = Product
                .builder()
                .productName("iPhone9S")
                .skuNumber("24")
                .inventoryCount(100)
                .description("iPhone 9S")
                .price(899.99)
                .productCategory(savedProductCategory)
                .createdDate(now)
                .build();

        savedProductCategory.setProducts(Arrays.asList(savedProduct1, savedProduct2));

        when(mockCartRepository.findByCartName("MyFirstCart")).thenReturn(savedCart);
        when(mockProductRepository.findBySkuNumber("22")).thenReturn(savedProduct1);
        when(mockProductRepository.findBySkuNumber("24")).thenReturn(savedProduct2);

        savedItem = Item
                .builder()
                .itemId(1L)
                .cart(savedCart)
                .product(savedProduct1)
                .quantity(2)
                .createdDate(now)
                .build();

        when(mockItemRepository.save(any(Item.class))).thenReturn(savedItem);

        item1 = Item
                .builder()
                .itemId(1L)
                .quantity(1)
                .product(savedProduct1)
                .cart(savedCart)
                .createdDate(now)
                .build();

        item2 = Item
                .builder()
                .itemId(2L)
                .quantity(2)
                .product(savedProduct2)
                .cart(savedCart)
                .createdDate(now)
                .build();
    }

    @Test
    public void addItem_findsCartByCartName() throws CartNotFoundException, ProductNotFoundException {
        subject.addItem(AddItemRequest
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .quantity(2)
                .build());

        verify(mockCartRepository).findByCartName("MyFirstCart");
    }

    @Test
    public void addItem_findsProductBySkuNumber() throws CartNotFoundException, ProductNotFoundException {
        subject.addItem(AddItemRequest
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .quantity(2)
                .build());

        verify(mockProductRepository).findBySkuNumber("22");
    }

    @Test
    public void addItem_addsItem() throws CartNotFoundException, ProductNotFoundException {
        ItemResponse actual = subject.addItem(AddItemRequest
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .quantity(2)
                .build());

        ItemResponse expected = ItemResponse
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .price(799.99)
                .quantity(2)
                .build();

        verify(mockItemRepository).save(Item
                .builder()
                .cart(savedCart)
                .product(savedProduct1)
                .quantity(2)
                .createdDate(any(LocalDateTime.class))
                .build());

       assertEquals(expected, actual);
    }

    @Test
    public void addItem_decrementsProductInventoryCount() throws CartNotFoundException, ProductNotFoundException {
        subject.addItem(AddItemRequest
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .quantity(2)
                .build());

        verify(mockItemRepository).save(itemArgumentCaptor.capture());

        assertEquals(98, itemArgumentCaptor.getValue().getProduct().getInventoryCount().intValue());
    }

    // TODO - immersion - 3.1
    // TODO - Insufficient Product Inventory Exception
    @Test
    public void addItem_onInsufficientProductInventory_throwsInsufficientProductInventoryException() {
    }

    // TODO - immersions - 2.1
    @Test
    public void deleteItem_deletesItem() {
    }

    // TODO - immersions - 2.1
    // TODO - immersions - 2.1 - add back to product inventory count
    @Test
    public void deleteItem_incrementsProductInventoryCount() {
    }

    @Test(expected = CartNotFoundException.class)
    public void addItem_findByCartNameWithInvalidCartName_throwsCartNotFoundException() throws CartNotFoundException, ProductNotFoundException {
        subject.addItem(AddItemRequest
                .builder()
                .cartName("InvalidCart")
                .skuNumber("22")
                .quantity(2)
                .build());
    }

    @Test(expected = ProductNotFoundException.class)
    public void addItem_findByProductWithInvalidSkuNumber_throwsProductNotFoundException() throws CartNotFoundException, ProductNotFoundException {
        subject.addItem(AddItemRequest
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("InvalidSKU")
                .quantity(2)
                .build());
    }

    @Test
    public void retrieveItems_retrievesItemsInCart() throws Exception {
        savedCart.setItems(Arrays.asList(item1, item2));
        when(mockCartRepository.findByCartName("MyFirstCart")).thenReturn(savedCart);
        when(mockItemRepository.findItemsByCart(savedCart)).thenReturn(Arrays.asList(item1, item2));


        List<ItemResponse> actual = subject.retrieveItems("MyFirstCart");

        verify(mockCartRepository).findByCartName("MyFirstCart");

        List<ItemResponse> expected = Arrays.asList(ItemResponse
                .builder()
                .cartName("MyFirstCart")
                .skuNumber("22")
                .quantity(1)
                .price(799.99)
                .build(),
                ItemResponse
                        .builder()
                        .cartName("MyFirstCart")
                        .skuNumber("24")
                        .quantity(2)
                        .price(899.99)
                        .build());
        assertEquals(expected, actual);
    }

    @Test(expected = CartNotFoundException.class)
    public void retrieveItems_findByCartNameWithInvalidCartName_throwsCartNotFoundException() throws CartNotFoundException, ProductNotFoundException {
        subject.retrieveItems("InvalidCart");
    }
}