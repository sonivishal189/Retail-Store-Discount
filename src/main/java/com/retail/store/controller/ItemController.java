package com.retail.store.controller;

import com.retail.store.entity.Item;
import com.retail.store.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public Item createItem(@RequestBody Item item) {
        log.info("Create Item: {}", item);
        return itemService.createItem(item);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable int id) {
        log.info("Get Item By Id: {}", id);
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable int id, @RequestBody Item item) {
        log.info("Update Item: {}", item);
        return itemService.updateItem(id, item);
    }
}
