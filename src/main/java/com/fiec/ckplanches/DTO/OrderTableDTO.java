package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.enums.ExitMethod;
import com.fiec.ckplanches.model.enums.OrderStatus;

public record OrderTableDTO(Integer orderId,
    OrderStatus orderStatus,
    String customerName,
    ExitMethod exitMethod,
    String paymentMethod,
    Double totalValue,
    Double subValue,
    LocalDateTime endDateTime,
    List<OrderProductTableDTO> orderProductTableDTOs,
    DeliveryTableDTO deliveryDTO
    ) {}

