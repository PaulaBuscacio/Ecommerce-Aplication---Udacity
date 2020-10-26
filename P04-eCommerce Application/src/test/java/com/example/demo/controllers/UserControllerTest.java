package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {

        userController = new UserController();
        testUtils.injectObjects(userController, "userRepository", userRepo);
        testUtils.injectObjects(userController, "cartRepository", cartRepo);
        testUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

    }

    @Test
    public void create_user_happy_path() throws  Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");


        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void verify_username_happy_path() throws  Exception {
        CreateUserRequest cr = new CreateUserRequest();
        cr.setUsername("Name");
        cr.setPassword("password");
        cr.setConfirmPassword("password");

        final ResponseEntity<User> response1 = userController.createUser(cr);
        User user = response1.getBody();
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        final ResponseEntity<User> response2 = userController.findByUserName(user.getUsername());

        User user1 = response2.getBody();

        assertNotNull(response1);
        assertEquals(200, response1.getStatusCodeValue());
        assertNotNull(response2);
        log.info(String.valueOf(response2));
        assertEquals(200, response2.getStatusCodeValue());
        assertEquals("Name", user1.getUsername());
    }

    @Test
    public void verify_userId_happy_path() throws  Exception {
        CreateUserRequest cr = new CreateUserRequest();
        cr.setUsername("Name");
        cr.setPassword("password");
        cr.setConfirmPassword("password");

        final ResponseEntity<User> response1 = userController.createUser(cr);
        User user = response1.getBody();
        Mockito.when(userRepo.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        ResponseEntity<User> response2 = userController.findById(user.getId());

        assertEquals(200, response2.getStatusCodeValue());
        assertEquals("Name", response2.getBody().getUsername());
        log.info(String.valueOf(response2));
    }


}
