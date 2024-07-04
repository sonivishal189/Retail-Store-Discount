package com.retail.store.controller;

import com.retail.store.entity.Item;
import com.retail.store.model.ServiceResponse;
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
    public ServiceResponse<Item> createItem(@Valid @RequestBody Item item) {
        log.info("Create Item: {}", item);
        Item newItem = itemService.createItem(item);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, newItem);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Item by Id")
    public ServiceResponse<Item> getItemById(@PathVariable int id) {
        log.info("Get Item By Id: {}", id);
        Item itemById = itemService.getItemById(id);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, itemById);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Items")
    public ServiceResponse<List<Item>> getAllItem() {
        log.info("Get All Item");
        List<Item> allItem = itemService.getAllItem();
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, allItem);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update Item")
    public ServiceResponse<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        log.info("Update Item: {}", item);
        Item updatedItem = itemService.updateItem(id, item);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, updatedItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Item by Id")
    public ServiceResponse<String> deleteItem(@PathVariable int id) {
        log.info("Delete Item: {}", id);
        String deleteMessage = itemService.deleteItem(id);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, deleteMessage, null);
    }
}
