package com.fiec.ckplanches.model.productOrder;

import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto_pedido")
@NoArgsConstructor
@Getter
@Setter
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "fk_produto", nullable=false)
    private Product product;

    private int quantidade;
    private double preco;
    private String observacao;
}
