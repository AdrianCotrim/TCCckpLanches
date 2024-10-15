package com.fiec.ckplanches.DTO;

import java.util.List;

import com.fiec.ckplanches.model.enums.ExitMethod;
import com.fiec.ckplanches.model.enums.OrderStatus;

public record OrderUpdateDTO(
    int id,
    OrderStatus orderStatus, 
    String customerName, 
    ExitMethod exitMethod, 
    String paymentMethod, 
    List<OrderProductDTO> orderProductDTOs,
    DeliveryDTO deliveryDTO
) {
}