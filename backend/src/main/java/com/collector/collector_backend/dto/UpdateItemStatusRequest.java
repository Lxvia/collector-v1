package com.collector.collector_backend.dto;

import com.collector.collector_backend.entity.ItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemStatusRequest {

    @NotNull(message = "Le statut est obligatoire")
    private ItemStatus status;

}
