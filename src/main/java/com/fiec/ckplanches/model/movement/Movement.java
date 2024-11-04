package com.fiec.ckplanches.model.movement;

import java.time.LocalDateTime;

import com.fiec.ckplanches.model.enums.Status;
import com.fiec.ckplanches.model.enums.TypeMovement;
import com.fiec.ckplanches.model.lot.Lot;
import com.fiec.ckplanches.model.supply.Supply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movimentacao")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_movimentacao")
    private int id;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime movementDate;
    
    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TypeMovement type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    // @ManyToOne
    // @JoinColumn(name = "fk_fornecedor", nullable = true)
    // private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "fk_lote", nullable = true)
    private Lot lot;

    @ManyToOne
    @JoinColumn(name = "fk_insumo", nullable = true)
    private Supply supply;

    public Movement(LocalDateTime movementDate, int quantity, TypeMovement type, Lot lot, Supply supply) {
        this.movementDate = movementDate;
        this.quantity = quantity;
        this.type = type;
        this.lot = lot;
        this.supply = supply;
        this.status = Status.ATIVO;
    }

    public Movement(LocalDateTime movementDate, int quantity, TypeMovement type, Supply supply) {
        this.movementDate = movementDate;
        this.quantity = quantity;
        this.type = type;
        this.supply = supply;
        this.status = Status.ATIVO;
    }

    

    

    
    
}
