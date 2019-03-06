package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.entities.Item;
import com.corelogic.sc.entities.Product;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.exceptions.InsufficientProductInventoryException;
import com.corelogic.sc.exceptions.ItemNotFoundException;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddItemRequest;
import com.corelogic.sc.requests.DeleteItemRequest;
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

    public ItemResponse addItem(AddItemRequest addItemRequest)
            throws CartNotFoundException, ProductNotFoundException, InsufficientProductInventoryException {
        Cart cart = cartRepository.findByCartName(addItemRequest.getCartName());
        if (cart == null) {
            throw new CartNotFoundException("Cart " + addItemRequest.getCartName() + " was not found");
        }

        Product product = productRepository.findBySkuNumber(addItemRequest.getSkuNumber());
        if (product == null) {
            throw new ProductNotFoundException("No product exists for sku# " + addItemRequest.getSkuNumber());
        }

        if (product.getInventoryCount() < addItemRequest.getQuantity()) {
            throw new InsufficientProductInventoryException("Insufficient inventory count for product sku# "
                    + addItemRequest.getSkuNumber());
        }
        product.setInventoryCount(product.getInventoryCount() - addItemRequest.getQuantity());

        Item item = itemRepository.save(Item
                .builder()
                .cart(cart)
                .product(product)
                .quantity(addItemRequest.getQuantity())
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

    public ItemResponse deleteItem(DeleteItemRequest deleteItemRequest) throws CartNotFoundException, ProductNotFoundException, ItemNotFoundException {
        Item savedItem = itemRepository.findBySkuNumber(deleteItemRequest.getSkuNumber());
        if (savedItem == null) {
            throw new ItemNotFoundException("Item " + deleteItemRequest.getSkuNumber() + " was not found");
        }

        Cart cart = cartRepository.findByCartName(deleteItemRequest.getCartName());
        if (cart == null) {
            throw new CartNotFoundException("Cart " + deleteItemRequest.getCartName() + " was not found");
        }

        Product product = productRepository.findBySkuNumber(deleteItemRequest.getSkuNumber());
        if (product == null) {
            throw new ProductNotFoundException("No product exists for sku# " + deleteItemRequest.getSkuNumber());
        }
        product.setInventoryCount(product.getInventoryCount() + deleteItemRequest.getQuantity());

        Integer updatedQuantity = savedItem.getQuantity() - deleteItemRequest.getQuantity();

        if (updatedQuantity == 0) {
            cart.setItems(cart
                    .getItems()
                    .stream()
                    .filter(item -> !item.getItemId().equals(savedItem.getItemId()))
                    .collect(Collectors.toList()));

            product.setItems(product
                    .getItems()
                    .stream()
                    .filter(item -> !item.getItemId().equals(savedItem.getItemId()))
                    .collect(Collectors.toList()));
            itemRepository.delete(savedItem.getItemId());
        } else {
            savedItem.setQuantity(updatedQuantity);
        }
        productRepository.save(product);

        return ItemResponse
                .builder()
                .quantity(deleteItemRequest.getQuantity())
                .cartName(cart.getCartName())
                .skuNumber(savedItem.getProduct().getSkuNumber())
                .price(savedItem.getProduct().getPrice())
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
