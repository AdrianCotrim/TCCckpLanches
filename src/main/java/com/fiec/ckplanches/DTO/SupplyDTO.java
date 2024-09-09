package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

public record SupplyDTO( 
    int id,
    String name,
    int quantity,
    int minQuantity,
    int maxQuantity,
    LocalDateTime experation_date
) {
}