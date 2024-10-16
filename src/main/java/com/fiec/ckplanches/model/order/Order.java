package com.fiec.ckplanches.model.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import com.fiec.ckplanches.model.delivery.Delivery;
import com.fiec.ckplanches.model.enums.ExitMethod;
import com.fiec.ckplanches.model.enums.OrderStatus;
import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.productOrder.ProductOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_pedido", nullable = false)
    private Integer orderId;

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false, length = 10)
    private OrderStatus orderStatus;

    @Column(name = "nome_cliente", nullable = false, length = 255)
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_saida", nullable = false, length = 50)
    private ExitMethod exitMethod;

    @Column(name = "forma_pagamento", nullable = false, length = 50)
    private String paymentMethod;

    private Double totalValue;

    @Column(name = "data_hora_saida", nullable = false)
    private LocalDateTime exitDatetime;

    @Column(name = "data_hora_finalizacao", nullable = false)
    private LocalDateTime endDatetime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // Relacionamento OneToOne para a entrega
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", referencedColumnName = "deliveryId", nullable = true)
    private Delivery delivery;

}
