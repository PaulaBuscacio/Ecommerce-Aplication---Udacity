package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserController userController;
    private CartRepository cartRepo = mock(CartRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {

        cartController = new CartController();
        testUtils.injectObjects(cartController, "cartRepository", cartRepo);
        testUtils.injectObjects(cartController, "userRepository", userRepo);
        testUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void addToCart_happy_path() throws  Exception {
        ModifyCartRequest cr = new ModifyCartRequest();
        User u = new User();
        Optional<Item> item = Optional.of(new Item());
        item.get().setPrice(BigDecimal.valueOf(1.99));
        item.get().setId((long)1);

        when(userRepo.findByUsername(cr.getUsername())).thenReturn(u);

        when(itemRepo.findById(cr.getItemId())).thenReturn(item);

        final ResponseEntity<Cart> response = cartController.addTocart(cr);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void remove_from_cart_happy_path() throws Exception {

        ModifyCartRequest cr = new ModifyCartRequest();
        User u = new User();
        Optional<Item> item = Optional.of(new Item());

        when(userRepo.findByUsername(cr.getUsername())).thenReturn(u);

        when(itemRepo.findById(cr.getItemId())).thenReturn(item);

        final ResponseEntity<Cart> response = cartController.removeFromcart(cr);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }



}
