package com.retail.store.controller;

import com.retail.store.entity.Item;
import com.retail.store.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/item")
@Tag(name = "Item Management", description = "APIs to manage Item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    @Operation(summary = "Create new Item")
    public Item createItem(@Valid @RequestBody Item item) {
        log.info("Create Item: {}", item);
        return itemService.createItem(item);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Item by Id")
    public Item getItemById(@PathVariable int id) {
        log.info("Get Item By Id: {}", id);
        return itemService.getItemById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Items")
    public List<Item> getAllItem() {
        log.info("Get All Item");
        return itemService.getAllItem();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update Item")
    public Item updateItem(@PathVariable int id, @RequestBody Item item) {
        log.info("Update Item: {}", item);
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Item by Id")
    public String deleteItem(@PathVariable int id) {
        log.info("Delete Item: {}", id);
        return itemService.deleteItem(id);
    }
}
