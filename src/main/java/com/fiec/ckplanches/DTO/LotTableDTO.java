package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;


public record LotTableDTO(
    int id,
    LocalDateTime expirationDate,
    int quantity,
    SupplyTableDTO supplyTableDTO,
    SupplierTableDTO supplierTableDTO
) {
    
}
