package com.fiec.ckplanches.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_pedido", nullable = false)
    private Integer orderId;


    @ManyToMany
    @JoinTable(
        name = "produto_pedido",
        joinColumns = @JoinColumn(name = "fk_pedido"),
        inverseJoinColumns = @JoinColumn(name = "fk_produto")
    )
    private List<Product> products;


    @Column(name = "estado_pedido", nullable = false, length = 25)
    private String orderStatus;

    @Column(name = "nome_cliente", nullable = false, length = 255)
    private String customerName;

    @Column(name = "forma_saida", nullable = false, length = 50)
    private String exitMethod;
 

    @Column(name = "forma_pagamento", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "valor_total", nullable = false)
    private Double totalValue;

    @Column(name = "data_hora_saida", nullable = false)
    private LocalDateTime exitDatetime;

    @Column(name = "data_hora_finalizacao", nullable = false)
    private LocalDateTime endDatetime;

    
}