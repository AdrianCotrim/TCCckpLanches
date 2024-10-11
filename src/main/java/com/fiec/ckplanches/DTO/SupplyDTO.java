package com.fiec.ckplanches.DTO;

public record SupplyDTO(
    String description,
    Integer maxQuantity,
    Integer minQuantity,
    String name
) {
}
