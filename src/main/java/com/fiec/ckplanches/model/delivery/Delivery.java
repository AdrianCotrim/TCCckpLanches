package com.fiec.ckplanches.model.delivery;

import com.fiec.ckplanches.model.enums.Change;
import com.fiec.ckplanches.model.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="entrega")
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId", nullable=false)
    private Integer deliveryId;

    @Column(name = "motoboy", nullable = false)
    private String motoboy;

    @Column(name="endereco", nullable = false)
    private String address;

    @Column(name = "complemento", nullable = true)
    private String complement;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrocoSimNao", nullable = false)
    private Change change;
    
    @Column(name = "taxaEntrega", nullable = false)
    private Double fee;

    @OneToOne(mappedBy="delivery")
    private Order order;
    
}