package com.fiec.ckplanches.DTO;

public record SupplyDTO(
    String description,
    int lot,
    int maxQuantity,
    int minQuantity,
    String name,
    int quantity
) {
}
