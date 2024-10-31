package com.fiec.ckplanches.DTO;

import com.fiec.ckplanches.model.enums.Change;

public record DeliveryDTO(
    String motoboy,
    String address,
    String complement,
    String telefone,
    Change change,
    Double fee
) {
    
}
