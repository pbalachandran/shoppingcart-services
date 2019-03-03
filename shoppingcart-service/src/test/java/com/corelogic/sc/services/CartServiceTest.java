package com.corelogic.sc.services;

import com.corelogic.sc.entities.Cart;
import com.corelogic.sc.exceptions.CartNotFoundException;
import com.corelogic.sc.requests.AddCartRequest;
import com.corelogic.sc.requests.DeleteCartRequest;
import com.corelogic.sc.responses.CartResponse;
import com.corelogic.sc.respositories.CartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {


    @Mock
    private CartRepository mockCartRepository;

    private CartService subject;

    @Before
    public void setUp() throws Exception {
        subject = new CartService(mockCartRepository);
    }

    @Test
    public void createCart_createsCart() {

        Cart savedCart = Cart.builder().cartName("Cart101").description("MyFirstCart").build();
        when(mockCartRepository.save(Cart.builder().cartName("Cart101").description("MyFirstCart").build())).thenReturn(savedCart);

        CartResponse actual = subject.createCart(AddCartRequest.builder().cartName("Cart101").description("MyFirstCart").build());

        CartResponse expected = CartResponse.builder().cartName("Cart101").description("MyFirstCart").build();
        verify(mockCartRepository).save(Cart.builder().cartName("Cart101").description("MyFirstCart").build());
        assertEquals(expected, actual);
    }

    @Test
    public void findCart_findsCartByCartName() throws Exception {
        Cart savedCart = Cart.builder().cartName("Cart101").description("MyFirstCart").build();
        when(mockCartRepository.findByCartName("Cart101")).thenReturn(savedCart);

        CartResponse actual = subject.findCart("Cart101");

        CartResponse expected = CartResponse.builder().cartName("Cart101").description("MyFirstCart").build();
        verify(mockCartRepository).findByCartName("Cart101");
        assertEquals(expected, actual);
    }

    @Test(expected = CartNotFoundException.class)
    public void findCart_doesNotFindCartByThatCartName_throwsCartNotFoundException() throws Exception {
        when(mockCartRepository.findByCartName("Cart101")).thenReturn(null);

        subject.findCart("Cart101");
        verify(mockCartRepository).findByCartName("Cart101");
    }

    @Test
    public void deleteCart_deletesCart() throws CartNotFoundException {
        doNothing().when(mockCartRepository).delete("MyFirstCart");

        subject.deleteCart(DeleteCartRequest
                .builder()
                .cartName("MyFirstCart")
                .build());
        verify(mockCartRepository).delete("MyFirstCart");
    }

    // TODO - immersion - 1.1 - transitive item deletion, using itemService
    @Test
    public void deleteCart_invokesItemService() throws CartNotFoundException {
    }

    @Test(expected = CartNotFoundException.class)
    public void deleteCart_doesNotFindCartByThatCartName_throwsCartNotFoundException() throws Exception {
        doThrow(CartNotFoundException.class).when(mockCartRepository).delete("InvalidCart");

        subject.findCart("InvalidCart");
    }
}