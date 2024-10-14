package com.fiec.ckplanches.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.enums.ExitMethod;
import com.fiec.ckplanches.model.enums.OrderStatus;

public record OrderDTO(OrderStatus orderStatus, String customerName, ExitMethod exitMethod, String paymentMethod, LocalDateTime endDateTime, LocalDateTime exitDateTime, List<OrderProductDTO> orderProductDTOs) {

}