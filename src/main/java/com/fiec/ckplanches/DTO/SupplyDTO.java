package com.fiec.ckplanches.DTO;

public record SupplyDTO(
    String description,
    Integer lot,
    Integer maxQuantity,
    Integer minQuantity,
    String name,
    Integer quantity
) {
}
