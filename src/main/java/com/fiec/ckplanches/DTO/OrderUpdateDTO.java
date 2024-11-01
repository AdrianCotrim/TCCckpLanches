package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;

import com.fiec.ckplanches.model.enums.ExitMethod;
import com.fiec.ckplanches.model.enums.OrderStatus;

public record OrderUpdateDTO(
    int id,
    OrderStatus orderStatus, 
    String customerName, 
    ExitMethod exitMethod,
    String paymentMethod,
    LocalDateTime endDateTime,
    LocalDateTime exitDateTime,
    DeliveryDTO deliveryDTO
) {
}