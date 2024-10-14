package com.fiec.ckplanches.DTO;

public class OrderRequestDTO {
    private OrderDTO orderDTO;
    private DeliveryDTO deliveryDTO;

    // Getters e Setters
    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public DeliveryDTO getDeliveryDTO() {
        return deliveryDTO;
    }

    public void setDeliveryDTO(DeliveryDTO deliveryDTO) {
        this.deliveryDTO = deliveryDTO;
    }
}
