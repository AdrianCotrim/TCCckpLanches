package com.fiec.ckplanches.model.supply;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.product.Product;
import com.fiec.ckplanches.model.purchase.Purchase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "insumo")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "data_validade", nullable = false)
    private LocalDateTime expiration_date;
    @Column(name = "descricao", nullable = false)
    private String description;
    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @ManyToMany(mappedBy = "supplies", cascade = CascadeType.ALL)
    private List<Purchase> purchases;

    @ManyToMany(mappedBy = "supplies", cascade = CascadeType.ALL)
    private List<Product> products;
}
