package com.fiec.ckplanches.DTO;

import java.util.List;

public record ProductTableDTO(Integer product_id, String product_name, Double product_value, String pathImage, String description, List<SupplyDTO> supplies) {
}
