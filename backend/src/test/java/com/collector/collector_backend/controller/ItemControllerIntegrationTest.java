package com.collector.collector_backend.controller;

import com.collector.collector_backend.dto.ItemResponse;
import com.collector.collector_backend.entity.Item;
import com.collector.collector_backend.entity.ItemStatus;
import com.collector.collector_backend.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.collector.collector_backend.service.ItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceGetAllTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getAllItems_shouldReturnEmptyList_whenNoItems() {
        when(itemRepository.findAll()).thenReturn(List.of());

        List<ItemResponse> result = itemService.getAllItems();

        assertThat(result).isEmpty();
    }

    @Test
    void getAllItems_shouldReturnMappedItems() {
        Item item = Item.builder()
                .id(1L)
                .title("Poster Star Wars")
                .description("Original 1977")
                .price(50.00)
                .status(ItemStatus.APPROVED)
                .createdAt(LocalDateTime.now())
                .build();

        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<ItemResponse> result = itemService.getAllItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Poster Star Wars");
        assertThat(result.get(0).status()).isEqualTo("APPROVED");
    }
}