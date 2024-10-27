package com.fiec.ckplanches.DTO;

import java.util.List;
import com.fiec.ckplanches.model.enums.Category;

public record ProductTableDTO(Integer product_id, String product_name, Double product_value, String pathImage, String description, Category category,List<SupplyTableDTO> supplies) {
}
