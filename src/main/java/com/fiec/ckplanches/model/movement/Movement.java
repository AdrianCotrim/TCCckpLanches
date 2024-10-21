package com.fiec.ckplanches.model.movement;

import java.time.LocalDateTime;
import java.util.List;

import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.supplier.Supplier;
import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "movimentacao")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_compra")
    private int id;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime movementDate;

    @Column(name = "valor", nullable = false)
    private double value;
    
    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TypeMovement type;

    @ManyToOne
    @JoinColumn(name = "fk_fornecedor", nullable = true)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "fk_insumo")
    private Supply supply;
}
