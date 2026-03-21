package com.collector.collector_backend.controller;

import com.collector.collector_backend.dto.ItemRequest;
import com.collector.collector_backend.dto.ItemResponse;
import com.collector.collector_backend.dto.UpdateItemStatusRequest;
import com.collector.collector_backend.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemResponse createItem(@Valid @RequestBody ItemRequest request) {
        return itemService.createItem(request);
    }

    @GetMapping
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @PatchMapping("/{id}/status")
    public ItemResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemStatusRequest request
    ) {
        return itemService.updateStatus(id, request.getStatus());
    }
}
