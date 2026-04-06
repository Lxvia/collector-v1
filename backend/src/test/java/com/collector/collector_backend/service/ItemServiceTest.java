package com.collector.collector_backend.service;

import com.collector.collector_backend.dto.ItemRequest;
import com.collector.collector_backend.dto.ItemResponse;
import com.collector.collector_backend.entity.Item;
import com.collector.collector_backend.entity.ItemStatus;
import com.collector.collector_backend.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .title("Figurine Star Wars")
                .description("Figurine originale 1977")
                .price(150.00)
                .status(ItemStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createItem_shouldReturnItemResponse() {
        // Given
        ItemRequest request = new ItemRequest();
        request.setTitle("Figurine Star Wars");
        request.setDescription("Figurine originale 1977");
        request.setPrice(150.00);

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemResponse response = itemService.createItem(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("Figurine Star Wars");
        assertThat(response.price()).isEqualByComparingTo(150.00);
    }

    @Test
    void updateStatus_shouldThrowException_whenItemNotFound() {
        // Given
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> itemService.updateStatus(99L, ItemStatus.APPROVED))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Item introuvable");
    }

    @Test
    void updateStatus_shouldUpdateAndReturnResponse() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        item.setStatus(ItemStatus.APPROVED);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemResponse response = itemService.updateStatus(1L, ItemStatus.APPROVED);

        // Then
        assertThat(response.status()).isEqualTo("APPROVED");
    }
}