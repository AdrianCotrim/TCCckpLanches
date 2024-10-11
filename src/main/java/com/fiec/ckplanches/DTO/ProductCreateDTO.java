package com.fiec.ckplanches.DTO;

import java.util.List;

import com.fiec.ckplanches.model.enums.Category;
import com.fiec.ckplanches.model.supply.Supply;

public record ProductCreateDTO(
    String productName,
    Double productValue,
    String description,
    List<Supply> supplies,
    Category category
) {
    
}
