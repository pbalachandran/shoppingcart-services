package com.corelogic.sc.db.controllers;


import com.corelogic.sc.db.entities.Cart;
import com.corelogic.sc.db.entities.Item;
import com.corelogic.sc.db.entities.Product;
import com.corelogic.sc.db.entities.ProductCategory;
import com.corelogic.sc.db.respositories.CartRepository;
import com.corelogic.sc.db.respositories.ItemRepository;
import com.corelogic.sc.db.respositories.ProductCategoryRepository;
import com.corelogic.sc.db.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("/api/db")
public class DbController {

    private ProductCategoryRepository productCategoryRepository;

    private ProductRepository productRepository;

    private CartRepository cartRepository;

    private ItemRepository itemRepository;

    @Autowired
    public DbController(ProductCategoryRepository productCategoryRepository,
                        ProductRepository productRepository,
                        CartRepository cartRepository,
                        ItemRepository itemRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    @DeleteMapping(value = "/wipe")
    public void wipe() {
        itemRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @PostMapping(value = "seed")
    public void seed() {
        LocalDateTime now = LocalDateTime.now();

        ProductCategory electronics = ProductCategory
                .builder()
                .createdDate(now)
                .productCategoryName("Electronics")
                .description("Electronics & Computer Equipment")
                .build();

        ProductCategory lawnAndGarden = ProductCategory
                .builder()
                .createdDate(now)
                .productCategoryName("Lawn & Garden")
                .description("Lawn & Garden Equipment")
                .build();
        productCategoryRepository.save(Arrays.asList(electronics, lawnAndGarden));

        Product iphone8s = Product
                .builder()
                .createdDate(now)
                .skuNumber("IPHONE8S")
                .productName("iPhone 8S")
                .description("Apple iPhone 8S")
                .inventoryCount(100)
                .price(799.99)
                .productCategory(productCategoryRepository.findByProductCategoryName("Electronics"))
                .build();

        Product iphone9s = Product
                .builder()
                .createdDate(now)
                .skuNumber("IPHONE9S")
                .productName("iPhone 9S")
                .description("Apple iPhone 9S")
                .inventoryCount(100)
                .price(999.99)
                .productCategory(productCategoryRepository.findByProductCategoryName("Electronics"))
                .build();

        Product iPad10 = Product
                .builder()
                .createdDate(now)
                .skuNumber("IPAD10")
                .productName("iPad 10")
                .description("Apple iPad 10")
                .inventoryCount(100)
                .price(799.99)
                .productCategory(productCategoryRepository.findByProductCategoryName("Electronics"))
                .build();

        Product toroMower = Product
                .builder()
                .createdDate(now)
                .skuNumber("TOROMOWER567")
                .productName("Toro Mower 567")
                .description("Toro Mower 567")
                .inventoryCount(150)
                .price(1299.99)
                .productCategory(productCategoryRepository.findByProductCategoryName("Lawn & Garden"))
                .build();
        productRepository.save(Arrays.asList(iphone8s, iphone9s, iPad10, toroMower));

        Cart cart = Cart
                .builder()
                .createdDate(now)
                .cartName("MyFirstCart")
                .description("My First Cart")
                .build();
        cartRepository.save(cart);

        Item iPad10Item = Item
                .builder()
                .createdDate(now)
                .quantity(1)
                .cart(cartRepository.findByCartName("MyFirstCart"))
                .product(productRepository.findBySkuNumber("IPAD10"))
                .build();

        Item toroMowerItem = Item
                .builder()
                .createdDate(now)
                .quantity(1)
                .cart(cartRepository.findByCartName("MyFirstCart"))
                .product(productRepository.findBySkuNumber("TOROMOWER567"))
                .build();
        itemRepository.save(Arrays.asList(iPad10Item, toroMowerItem));
    }
}