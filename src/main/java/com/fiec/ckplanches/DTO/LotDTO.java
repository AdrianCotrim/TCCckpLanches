package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

public record LotDTO(
    int supplyId,
    int supplierId,
    int quantity,
    double value,
    LocalDateTime expirationDate
) {
    
}
