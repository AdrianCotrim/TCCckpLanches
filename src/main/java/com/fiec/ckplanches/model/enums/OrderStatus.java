package com.fiec.ckplanches.model.enums;

public enum OrderStatus {
    
    PREPARANDO("PREPARANDO"),
    PRONTO("PRONTO"),
    FINALIZADO("FINAZILADO");

    private String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }
}