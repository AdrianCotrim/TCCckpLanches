package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

import com.fiec.ckplanches.model.enums.TypeMovement;

public record MovementTableDTO(
    int id,
    LocalDateTime movementDate,
    int quantity,
    TypeMovement type,
    LotTableDTO lotTableDTO,
    SupplyTableDTO supplyTableDTO
) {
} 
