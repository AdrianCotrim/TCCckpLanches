package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

public record SupplyDTO( int id,
    LocalDateTime expirationDate,
    String description,
    int quantity,
    int minQuantity,
    int maxQuantity) {
}