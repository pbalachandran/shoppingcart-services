package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.requests.DeleteCartRequest;
import com.corelogic.sc.responses.CartResponse;
import com.corelogic.sc.respositories.CartRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResponse createCart(AddCartRequest addCartRequest) {
        Cart cart = Cart
                .builder()
                .cartName(addCartRequest.getCartName())
                .description(addCartRequest.getDescription()).build();
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

    // TODO - immersion - 1.1
    // TODO - immersion - 1.1 - transitive item deletion, using itemService
    public void deleteCart(DeleteCartRequest deleteCartRequest) throws CartNotFoundException {
        try {
            cartRepository.delete(deleteCartRequest.getCartName());
        } catch (EmptyResultDataAccessException ex) {
            throw new CartNotFoundException("Cart " + deleteCartRequest.getCartName() + " was not found");
        }
    }
}
