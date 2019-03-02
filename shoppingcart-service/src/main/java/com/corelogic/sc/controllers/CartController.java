package com.corelogic.sc.controllers;


import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.responses.CartExceptionResponse;
import com.corelogic.sc.responses.CartResponse;
import com.corelogic.sc.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/cart")
    public ResponseEntity<CartResponse> cart(@RequestBody AddCartRequest cartRequest) {
        return ResponseEntity.ok(cartService.createCart(cartRequest));
    }

    @GetMapping(value = "/cart/{cartName}")
    public ResponseEntity<CartResponse> cart(@PathVariable("cartName") String cartName) throws CartNotFoundException {
        return ResponseEntity.ok(cartService.findCart(cartName));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<CartExceptionResponse> cartNotFound(CartNotFoundException exception) {
        return new ResponseEntity<>(new CartExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}