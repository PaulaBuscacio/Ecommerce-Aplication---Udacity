package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    public void  javaaddToCart_happy_path() throws  Exception {

        User u = new User();
        u.setUsername("testUser");
        u.setPassword("passtest");
        Cart cart = new Cart();
        cart.setUser(u);
        u.setCart(cart);

        Optional<Item> item = Optional.of(new Item());
        item.get().setPrice(BigDecimal.valueOf(1.99));
        item.get().setId((long)1);
        item.get().setName("itemName");
        item.get().setDescription("This is an item.");

        ModifyCartRequest cr = new ModifyCartRequest();
        cr.setUsername("testUser");
        cr.setQuantity(1);
        cr.setItemId((1));


        Mockito.when(userRepo.findByUsername(cr.getUsername())).thenReturn(u);

        Mockito.when(itemRepo.findById(cr.getItemId())).thenReturn(item);

        final ResponseEntity<Cart> response = cartController.addTocart(cr);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart cartObj = response.getBody();

        assertEquals("testUser", cartObj.getUser().getUsername());
        assertEquals("itemName", cartObj.getItems().get(0).getName());

    }

    @Test
    public void remove_from_cart_happy_path() throws Exception {

        User u = new User();
        u.setUsername("testUser");
        u.setPassword("passtest");
        Cart cart = new Cart();
        cart.setUser(u);
        u.setCart(cart);

        Optional<Item> item = Optional.of(new Item());
        item.get().setPrice(BigDecimal.valueOf(1.99));
        item.get().setId((long)1);
        item.get().setName("itemName");
        item.get().setDescription("This is an item.");

        ModifyCartRequest cr = new ModifyCartRequest();
        cr.setUsername("testUser");
        cr.setQuantity(1);
        cr.setItemId((1));


        Mockito.when(userRepo.findByUsername(cr.getUsername())).thenReturn(u);

        Mockito.when(itemRepo.findById(cr.getItemId())).thenReturn(item);


        final ResponseEntity<Cart> response = cartController.removeFromcart(cr);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }



}
