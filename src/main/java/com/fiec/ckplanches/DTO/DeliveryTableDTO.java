package com.fiec.ckplanches.DTO;

import com.fiec.ckplanches.model.enums.Change;

public record DeliveryTableDTO(
    int id,
    String motoboy,
    String address,
    String complement,
    Change change,
    Double fee
) {
    
}
