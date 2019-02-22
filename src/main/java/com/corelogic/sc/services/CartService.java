package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.responses.CartResponse;
import com.corelogic.sc.respositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResponse createCart(AddCartRequest cartRequest) {
        Cart cart = Cart
                .builder()
                .cartName(cartRequest.getCartName())
                .description(cartRequest.getDescription()).build();
        Cart savedCart = cartRepository.save(cart);
        return CartResponse.builder().cartName(savedCart.getCartName()).description(savedCart.getDescription()).build();
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
                .build();
    }
}
