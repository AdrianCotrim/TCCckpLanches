package com.fiec.ckplanches.model.product;

import java.util.List;

import com.fiec.ckplanches.model.order.Order;
import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_produto")
    private Integer product_id;

    @Column(name = "nome_produto")
    private String product_name;

    @Column(name = "valor_produto")
    private Double product_value;

    @ManyToMany
    @JoinTable(
        name = "insumo_produto",
        joinColumns = @JoinColumn(name = "fk_produto"),
        inverseJoinColumns = @JoinColumn(name = "fk_insumo")
    )
    private List<Supply> supplies;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Order> orders;

}
