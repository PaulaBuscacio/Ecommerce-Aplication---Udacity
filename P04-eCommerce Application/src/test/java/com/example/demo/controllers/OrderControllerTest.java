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
        String username = new String();
        User user = new User();
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
        user.setUsername("username");
        UserOrder order = new UserOrder();
        order.setTotal(cart.getTotal());
        order.setUser(user);
        user.setCart(cart);
        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf(20.5));

        final ResponseEntity<UserOrder> response = orderController.submit("username");

        assertEquals(user, order.getUser());
        assertEquals(items, UserOrder.createFromCart(user.getCart()).getItems());
        assertEquals(BigDecimal.valueOf(20.5), cart.getTotal() );


    }
}


