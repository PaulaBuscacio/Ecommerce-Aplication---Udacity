package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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
        Item item1 = new Item();
        item1.setPrice(BigDecimal.valueOf(1.99));
        item1.setId((long)1);
        item1.setName("itemName");
        item1.setDescription("This is an item.");

        Item item2 = new Item();
        item2.setPrice(BigDecimal.valueOf(5.99));
        item2.setId((long)2);
        item2.setName("item2");
        item2.setDescription("This is another item.");

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        Mockito.when(itemRepo.findAll()).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> itemList = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("itemName", itemList.get(0).getName());
        assertEquals("item2", itemList.get(1).getName());

    }

    @Test
    public void get_item_id_happy_path() {
        Item item = new Item();
        item.setPrice(BigDecimal.valueOf(1.99));
        item.setId((long)1);
        item.setName("itemName");
        item.setDescription("This is an item.");

        Mockito.when(itemRepo.findById((long)1)).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById((long)1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(java.util.Optional.of((long) 1), java.util.Optional.of(item.getId()));
    }

}
