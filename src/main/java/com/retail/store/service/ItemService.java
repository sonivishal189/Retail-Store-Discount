package com.retail.store.service;

import com.retail.store.dao.ItemRepository;
import com.retail.store.entity.Item;
import com.retail.store.exception.ItemException;
import com.retail.store.util.ItemType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(int id) {
        if (itemRepository.findById(id).isPresent()) {
            Item item = itemRepository.findById(id).get();
            log.info("Item fetched by id: {}", item);
            return item;
        }
        log.error("Item not found with id: {}", id);
        throw new ItemException("Item not found with id: " + id);
    }

    public Item createItem(Item item) {
        item = itemRepository.save(item);
        log.info("Item created: {}", item);
        return item;
    }

    public Item updateItem(int id, Item item) {
        Item itemInDB = getItemById(id);
        itemInDB.setName(item.getName());
        itemInDB.setPrice(item.getPrice());
        itemInDB.setDescription(item.getDescription());
        itemInDB.setItemType(item.getItemType());
        item = itemRepository.save(itemInDB);
        log.info("Item updated: {}", item);
        return item;
    }

    @PostConstruct
    private void init() {
        List<Item> itemList = List.of(
                new Item(1, "Laptop", 60000.0, "HP Laptop", ItemType.ELECTRONIC),
                new Item(2, "TV", 30000.0, "Sony 4K UHD TV", ItemType.ELECTRONIC),
                new Item(3, "Men T-Shirt", 500.0, "UCB Men Polo T-Shirt", ItemType.CLOTHING),
                new Item(4, "Women T-Shirt", 400.0, "Mango Women T-Shirt", ItemType.CLOTHING),
                new Item(5, "Potato", 100.0, "Fresh Potato", ItemType.GROCERY),
                new Item(6, "Cheese", 200.0, "Amul Cheese", ItemType.GROCERY),
                new Item(7, "Think and Grow Rich", 300.0, "Think and Grow Rich", ItemType.BOOK),
                new Item(8, "Harry Potter", 300.0, "Harry Potter", ItemType.BOOK),
                new Item(9, "Pencil", 10.0, "Pilot Pencil", ItemType.OTHER),
                new Item(10, "Eraser", 5.0, "Pilot Eraser", ItemType.OTHER)
        );

        for (Item item : itemList) {
            createItem(item);
        }
    }
}
