package com.fiec.ckplanches.DTO;

import java.sql.Date;

public record SupplyDTO( 
    int id,
    String name,
    String description,
    int quantity,
    int minQuantity,
    int maxQuantity,
    Date experation_date
) {
}