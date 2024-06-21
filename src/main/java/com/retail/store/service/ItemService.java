package com.retail.store.service;

import com.retail.store.dao.ItemRepository;
import com.retail.store.exception.ItemException;
import com.retail.store.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(int id) {
        if(itemRepository.findById(id).isPresent()) {
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
}
