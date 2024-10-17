package com.fiec.ckplanches.model.supply;

import java.util.List;

import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.productSupply.ProductSupply;
import com.fiec.ckplanches.model.purchase.Purchase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    @Column(name = "pk_insumo")
    private int id;

    @Column(name = "name", nullable = false, length=100)
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @Column(name = "quantidade_minima", nullable = false)
    private int minQuantity;

    @Column(name = "quantidade_maxima", nullable = false)
    private int maxQuantity;

    @ManyToMany(mappedBy = "supplies", cascade = CascadeType.ALL)
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.REMOVE)
    private List<ProductSupply> productSupplies;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<Lot> lots;
}
