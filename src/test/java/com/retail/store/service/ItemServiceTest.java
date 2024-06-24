package com.retail.store.service;

import com.retail.store.dao.ItemRepository;
import com.retail.store.entity.Item;
import com.retail.store.exception.ItemException;
import com.retail.store.util.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    public static final int ITEM_ID = 1;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setItemId(ITEM_ID);
        item.setName("Laptop");
        item.setPrice(60000.0);
        item.setDescription("HP Laptop");
        item.setItemType(ItemType.ELECTRONIC);
    }

    @Test
    void testGetItemByIdSuccess() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(item));

        Item foundItem = itemService.getItemById(1);

        assertNotNull(foundItem);
        assertEquals(ITEM_ID, foundItem.getItemId());
    }

    @Test
    void testGetItemByIdNotFound() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.empty());

        ItemException exception = assertThrows(ItemException.class, () -> itemService.getItemById(ITEM_ID));
        assertTrue(exception.getMessage().contains("Item not found with id:"));
    }

    @Test
    void testGetAllItem() {
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> allItems = itemService.getAllItem();

        assertNotNull(allItems);
        assertEquals(1, allItems.size());
    }

    @Test
    void testCreateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item createdItem = itemService.createItem(item);

        assertNotNull(createdItem);
        assertEquals(item.getName(), createdItem.getName());
    }

    @Test
    void testUpdateItemSuccess() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item updateItem = new Item();
        updateItem.setItemId(ITEM_ID);
        updateItem.setName("Mobile");

        Item updatedItem = itemService.updateItem(ITEM_ID, updateItem);

        assertNotNull(updatedItem);
        assertEquals(updateItem.getName(), updatedItem.getName());
    }

    @Test
    void testDeleteItemSuccess() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(item));

        String response = itemService.deleteItem(ITEM_ID);

        assertTrue(response.contains("Item deleted with id:"));
    }

    @Test
    void testDeleteItemNotFound() {
        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.empty());

        ItemException exception = assertThrows(ItemException.class, () -> itemService.deleteItem(ITEM_ID));
        assertTrue(exception.getMessage().contains("Item not found with id:"));
    }

    @Test
    void testGetItemTypeByIdSuccess() {
        when(itemRepository.findItemTypeByItemId(1)).thenReturn(ItemType.ELECTRONIC);

        ItemType itemType = itemService.getItemTypeById(ITEM_ID);

        assertNotNull(itemType);
        assertEquals(ItemType.ELECTRONIC, itemType);
    }

    @Test
    void testInit() {
        itemService.init();
    }
}
