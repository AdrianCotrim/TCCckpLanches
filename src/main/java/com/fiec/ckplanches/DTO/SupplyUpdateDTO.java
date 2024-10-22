package com.fiec.ckplanches.DTO;

public record SupplyUpdateDTO(
    String name,
    String description,
    int minQuantity,
    int maxQuantity
) {
    
}
