package com.fiec.ckplanches.model.lot;

import java.sql.Date;

import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lote")
public class Lot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_validade", nullable = false)
    private Date expiration_date;

    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "fk_insumo", nullable = true)
    private Supply supply;

    @PrePersist
    public void addQuantitySupply(){
        supply.setQuantity(supply.getQuantity() + this.quantity);
    }
}
