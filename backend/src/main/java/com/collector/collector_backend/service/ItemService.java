package com.collector.collector_backend.service;

import com.collector.collector_backend.dto.ItemRequest;
import com.collector.collector_backend.dto.ItemResponse;
import com.collector.collector_backend.entity.Item;
import com.collector.collector_backend.entity.ItemStatus;
import com.collector.collector_backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponse createItem(ItemRequest request) {
        Item item = Item.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        Item saved = itemRepository.save(item);
        return mapToResponse(saved);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemResponse updateStatus(Long id, ItemStatus status) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item introuvable"));

        item.setStatus(status);
        Item saved = itemRepository.save(item);

        return mapToResponse(saved);
    }

    private ItemResponse mapToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getStatus().name(),
                item.getCreatedAt()
        );
    }
}