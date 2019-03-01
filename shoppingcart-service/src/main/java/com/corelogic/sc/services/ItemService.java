package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.entities.Item;
import com.corelogic.sc.entities.Product;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddItemRequest;
import com.corelogic.sc.responses.ItemResponse;
import com.corelogic.sc.respositories.CartRepository;
import com.corelogic.sc.respositories.ItemRepository;
import com.corelogic.sc.respositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    public ItemService(ItemRepository itemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public ItemResponse addItem(AddItemRequest itemRequest) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByCartName(itemRequest.getCartName());
        if (cart == null) {
            throw new CartNotFoundException("Cart " + itemRequest.getCartName() + " was not found");
        }

        Product product = productRepository.findBySkuNumber(itemRequest.getSkuNumber());
        if (product == null) {
            throw new ProductNotFoundException("No product exists for sku# " + itemRequest.getSkuNumber());
        }

        Item item = itemRepository.save(Item
                .builder()
                .cart(cart)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .createdDate(LocalDateTime.now())
                .build());

        return ItemResponse
                .builder()
                .quantity(item.getQuantity())
                .cartName(cart.getCartName())
                .skuNumber(item.getProduct().getSkuNumber())
                .price(item.getProduct().getPrice())
                .build();
    }

    public List<ItemResponse> retrieveItems(String cartName) throws CartNotFoundException {
        Cart cart = cartRepository.findByCartName(cartName);
        if (cart == null) {
            throw new CartNotFoundException("Cart " + cartName + " was not found");
        }

        List<Item> items = itemRepository.findItemsByCart(cart);
        return items.stream().map(item -> ItemResponse
                .builder()
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .skuNumber(item.getProduct().getSkuNumber())
                .cartName(item.getCart().getCartName())
                .build()).collect(Collectors.toList());
    }
}
