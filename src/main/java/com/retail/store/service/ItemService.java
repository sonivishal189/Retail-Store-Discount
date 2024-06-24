package com.retail.store.service;

import com.retail.store.dao.ItemRepository;
import com.retail.store.entity.Item;
import com.retail.store.exception.ItemException;
import com.retail.store.util.ItemType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(int id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            log.info("Item fetched by id: {}", item.get());
            return item.get();
        }
        log.error("Item not found with id: {}", id);
        throw new ItemException("Item not found with id: " + id);
    }

    public List<Item> getAllItem() {
        List<Item> allItem = (List<Item>) itemRepository.findAll();
        log.info("All items: {}", allItem);
        return allItem;
    }

    public Item createItem(Item item) {
        item = itemRepository.save(item);
        log.info("Item created: {}", item);
        return item;
    }

    public Item updateItem(int id, Item item) {
        Item itemInDB = getItemById(id);
        if (item.getName() != null && !item.getName().isEmpty()) {
            itemInDB.setName(item.getName());
        }
        if (item.getPrice() != 0.0) {
            itemInDB.setPrice(item.getPrice());
        }
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            itemInDB.setDescription(item.getDescription());
        }
        if (item.getItemType() != null) {
            itemInDB.setItemType(item.getItemType());
        }
        item = itemRepository.save(itemInDB);
        log.info("Item updated: {}", item);
        return item;
    }

    public String deleteItem(int id) {
        getItemById(id);
        itemRepository.deleteById(id);
        log.info("Item deleted with id: {}", id);
        return "Item deleted with id: " + id;
    }

    @PostConstruct
    public void init() {
        List<Item> itemList = List.of(
                new Item(1, "Laptop", 60000.0, "HP Laptop", ItemType.ELECTRONIC),
                new Item(2, "TV", 30000.0, "Sony 4K UHD TV", ItemType.ELECTRONIC),
                new Item(3, "Men T-Shirt", 500.0, "UCB Men Polo T-Shirt", ItemType.CLOTHING),
                new Item(4, "Women T-Shirt", 400.0, "Mango Women T-Shirt", ItemType.CLOTHING),
                new Item(5, "Potato", 50.0, "Fresh Potato", ItemType.GROCERY),
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

    public ItemType getItemTypeById(int itemId) {
        return itemRepository.findItemTypeByItemId(itemId);
    }
}
