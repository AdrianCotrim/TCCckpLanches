package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

public record LotDTO(
    Integer supplyId,
    Integer supplierId,
    Integer quantity, 
    LocalDateTime expirationDate,
    double value
) {
    
}
