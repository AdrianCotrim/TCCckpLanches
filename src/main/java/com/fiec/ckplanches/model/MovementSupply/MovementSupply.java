package com.fiec.ckplanches.model.MovementSupply;

import com.fiec.ckplanches.model.movement.Movement;
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
@Table(name = "momentacao_supply")
@NoArgsConstructor
@Getter
@Setter
public class MovementSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_movimentacao")
    private Movement movement;

    @ManyToOne
    @JoinColumn(name = "fk_supply")
    private Supply supply;

    @Column(name = "quantidade")
    private int quantity;
}
