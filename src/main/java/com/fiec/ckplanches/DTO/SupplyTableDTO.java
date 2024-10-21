package com.fiec.ckplanches.DTO;

public record SupplyTableDTO( 
    int id,
    String name,
    String description,
    int quantity,
    int minQuantity,
    int maxQuantity
) {
}