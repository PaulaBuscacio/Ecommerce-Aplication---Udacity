package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);


    @Before
    public void setUp() {

        itemController = new ItemController();
        testUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void get_item_happy_path() {

        List<Item> item = new ArrayList<>();

        final ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void get_item_id_happy_path() {
        Item item = new Item();
        item.setId((long) 1);

        assertEquals(java.util.Optional.of((long) 1), java.util.Optional.of(item.getId()));
    }

}
