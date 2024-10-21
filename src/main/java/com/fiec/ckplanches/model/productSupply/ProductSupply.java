package com.fiec.ckplanches.model.productSupply;

import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.Column;
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
@Table(name = "produto_insumo")
@Getter
@Setter
@NoArgsConstructor
public class ProductSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_produto_insumo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_produto", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fk_insumo", nullable = false)
    private Supply supply;
}
