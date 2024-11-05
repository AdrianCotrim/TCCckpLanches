package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;


public record LotTableDTO(
    int id,
    LocalDateTime expirationDate,
    LocalDateTime loteDate,
    int quantity,
    double value,
    SupplyTableDTO supplyTableDTO,
    SupplierTableDTO supplierTableDTO
) {
    
}
