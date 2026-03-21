package com.collector.collector_backend.dto;

import java.time.LocalDateTime;

public record ItemResponse(Long id, String title, String description, Double price, String status,
                           LocalDateTime createdAt) {

}
