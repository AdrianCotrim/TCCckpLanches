package com.fiec.ckplanches.DTO;

import com.fiec.ckplanches.model.product.Product;

public record OrderProductTableDTO(
    ProductTableDTO productTableDTO,
    int quantity,
    double preco,
    String observacao
) {
    
}
