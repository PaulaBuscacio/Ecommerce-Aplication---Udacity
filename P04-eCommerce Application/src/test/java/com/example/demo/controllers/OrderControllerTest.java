package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class OrderControllerTest {

    private OrderController orderController;
    private OrderRepository orderRepo = mock(OrderRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        testUtils.injectObjects(orderController, "orderRepository", orderRepo);
        testUtils.injectObjects(orderController, "userRepository", userRepo);
    }

    @Test
    public  void submit_order_happy_path() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        Item item = new Item();
        item.setPrice(BigDecimal.valueOf(1.99));
        item.setId((long)1);
        item.setName("itemName");
        item.setDescription("This is an item.");
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);

        UserOrder order = new UserOrder();
        order.setUser(user);
        order.setTotal(cart.getTotal());

        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());

        UserOrder response1 = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, order.getUser());
        assertEquals(items, UserOrder.createFromCart(user.getCart()).getItems());
        assertEquals("This is an item.", response1.getItems().get(0).getDescription());


    }
}


