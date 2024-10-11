package com.fiec.ckplanches.DTO;

import java.util.List;

import com.fiec.ckplanches.model.lot.Lot;

public record SupplyTableDTO( 
    int id,
    String name,
    String description,
    int quantity,
    int minQuantity,
    int maxQuantity,
    List<Lot> lots
) {
}