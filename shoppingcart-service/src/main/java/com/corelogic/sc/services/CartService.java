package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.entities.Item;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.exceptions.ItemNotFoundException;
import com.corelogic.sc.exceptions.ProductNotFoundException;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.requests.DeleteCartRequest;
import com.corelogic.sc.requests.RemoveItemFromCartRequest;
import com.corelogic.sc.responses.CartResponse;
import com.corelogic.sc.responses.CartStatus;
import com.corelogic.sc.respositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;

    private ItemService itemService;

    public CartService(CartRepository cartRepository, ItemService itemService) {
        this.cartRepository = cartRepository;
        this.itemService = itemService;
    }

    public CartResponse createCart(AddCartRequest addCartRequest) {
        Cart cart = Cart
                .builder()
                .cartName(addCartRequest.getCartName())
                .description(addCartRequest.getDescription())
                .status(CartStatus.ACTIVE.name())
                .build();
        Cart savedCart = cartRepository.save(cart);

        return CartResponse
                .builder()
                .cartName(savedCart.getCartName())
                .description(savedCart.getDescription())
                .status(CartStatus.getCartStatus(savedCart.getStatus()))
                .build();
    }

    public CartResponse findCart(String cartName) throws CartNotFoundException {
        Cart foundCart = cartRepository.findByCartName(cartName);
        if (foundCart == null) {
            throw new CartNotFoundException("Cart " + cartName + " was not found");
        }
        return CartResponse
                .builder()
                .cartName(foundCart.getCartName())
                .description(foundCart.getDescription())
                .status(CartStatus.getCartStatus(foundCart.getStatus()))
                .build();
    }

    public void deleteCart(DeleteCartRequest deleteCartRequest)
            throws CartNotFoundException, ProductNotFoundException, ItemNotFoundException {

        Cart cart = cartRepository.findByCartName(deleteCartRequest.getCartName());
        if (cart == null) {
            throw new CartNotFoundException("Cart " + deleteCartRequest.getCartName() + " was not found");
        }
        for (Item item : cart.getItems()) {
            itemService.removeItem(RemoveItemFromCartRequest.builder()
                    .skuNumber(item.getProduct().getSkuNumber())
                    .quantity(item.getQuantity())
                    .cartName(cart.getCartName())
                    .build());
        }
        cartRepository.deleteById(deleteCartRequest.getCartName());
    }
}
